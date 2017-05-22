package com.koniosoftworks.kvstreaming.data.client;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.domain.client.ClientListener;
import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessageRequest;
import com.koniosoftworks.kvstreaming.domain.dto.messages.DisconnectMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.props.ServerProperties;
import com.koniosoftworks.kvstreaming.utils.ImageConverter;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * Created by nicu on 5/15/17.
 */
public class ClientImpl implements Client {
    private ServerConnection serverConnection;
    private final PacketSerialization packetSerialization;
    private final EncodingAlgorithm encodingAlgorithm;
    private final TaskScheduler taskScheduler;
    private ClientListener clientListener;
    private String username;
    private final Logger logger;
    private DatagramSocket datagramSocket;

    @Inject
    public ClientImpl(PacketSerialization packetSerialization, EncodingAlgorithm encodingAlgorithm, TaskScheduler taskScheduler, Logger logger) {
        this.packetSerialization = packetSerialization;
        this.encodingAlgorithm = encodingAlgorithm;
        this.taskScheduler = taskScheduler;
        this.logger = logger;
    }

    @Override
    public void connect(ClientListener clientListener, String host, int port) throws IOException {
        this.clientListener = clientListener;
        try {
            Socket socket = new Socket(host, port);
            System.out.println("Connected to server");
            this.clientListener.onConnect();
            serverConnection = new ServerConnection(socket, packetSerialization, encodingAlgorithm,logger);
            taskScheduler.run(this::checkMessage);
        } catch (IOException e) {
            clientListener.onConnectionFailed(e.toString());
            logger.error(e);
            throw e;
        }
    }

    @Override
    public void disconnect() {
        serverConnection.close();
        serverConnection = null;
        clientListener = null;
        taskScheduler.unschedule(this::checkMessage);
        taskScheduler.unschedule(this::listenUdp);
        logger.info("Disconnected");
    }

    @Override
    public void sendMessage(String message) {
        Packet<ChatMessageRequest> packet = new Packet<>(PacketType.CHAT_MESSAGE_REQUEST, new ChatMessageRequest(message));
        try {
            serverConnection.send(packet);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void checkMessage() {
        while (true) {
            if (serverConnection.hasReceivedPacket()) {
                try {
                    Packet packet = serverConnection.getPacket();

                    switch (packet.getPacketType()) {
                        case INITIALIZATION:
                            InitializationMessage data = (InitializationMessage) packet.getData();
                            username = data.getUsername();
                            clientListener.onInitializationMessage(data);
                            taskScheduler.run(this::listenUdp);
                            break;
                        case CHAT_MESSAGE:
                            clientListener.onChatMessage((ChatMessage) packet.getData());
                            break;
                        case DISCONNECT:
                            clientListener.onDisconnect((DisconnectMessage) packet.getData());
                            serverConnection.close();
                            break;
                    }

                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }

    @Override
    public String getUsername() {
        if(serverConnection == null)
            throw new RuntimeException("Not connected");
        return username;
    }

    private void listenUdp() {
        try {
            datagramSocket = new DatagramSocket(ServerProperties.BROADCAST_PORT,InetAddress.getByName(ServerProperties.BROADCAST_LISTENING));

            byte[] buf = new byte[60000];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            while (true) {
                datagramSocket.receive(packet);
                byte[] bytes = Arrays.copyOf(buf, packet.getLength());

                clientListener.onNewStreamingImage(ImageConverter.byteArrayToImage(bytes));
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
