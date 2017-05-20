package com.koniosoftworks.kvstreaming.data.client;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.data.io.ScannerStreamReader;
import com.koniosoftworks.kvstreaming.data.io.SimpleStreamWriter;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.domain.client.ClientListener;
import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessageRequest;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.io.StreamReader;
import com.koniosoftworks.kvstreaming.domain.io.StreamWriter;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Created by nicu on 5/15/17.
 */
public class ClientImpl implements Client {
    private Socket socket;
    private final PacketSerialization packetSerialization;
    private final EncodingAlgorithm encodingAlgorithm;
    private final TaskScheduler taskScheduler;
    private StreamReader streamReader;
    private StreamWriter streamWriter;
    private ClientListener clientListener;

    @Inject
    public ClientImpl(PacketSerialization packetSerialization, EncodingAlgorithm encodingAlgorithm, TaskScheduler taskScheduler) {
        this.packetSerialization = packetSerialization;
        this.encodingAlgorithm = encodingAlgorithm;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void connect(ClientListener clientListener, String host, int port) {
        this.clientListener = clientListener;
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to server");
            this.clientListener.onConnect();
            streamReader = new ScannerStreamReader(socket.getInputStream());
            streamWriter = new SimpleStreamWriter(socket.getOutputStream());
            taskScheduler.schedule(this::checkMessage,100, TimeUnit.MILLISECONDS);
        } catch (IOException e) {
            clientListener.onConnectionFailed(e.toString());
            e.printStackTrace();
        }
    }


    @Override
    public void disconnect() {

    }

    @Override
    public void sendMessage(String message) {
        Packet<ChatMessageRequest> packet = new Packet<>(PacketType.CHAT_MESSAGE_REQUEST, new ChatMessageRequest(message));
        try {
            byte[] serialize = packetSerialization.serialize(packet);
            streamWriter.put(new String(serialize));
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkMessage(){
        if (socket.isClosed())
            taskScheduler.unschedule(this::checkMessage);
        if (!streamReader.hasNextString())
            return;

        byte[] bytes = encodingAlgorithm.decode(streamReader.nextString().getBytes());
        Packet packet = null;
        try {
            packet = packetSerialization.unserialize(bytes);

            switch (packet.getPacketType()){
                case INITITIALIZATION:
                    clientListener.onInitializationMessage((InitializationMessage) packet.getData());
                    break;
                case CHAT_MESSAGE:
                    clientListener.onChatMessage((ChatMessage)packet.getData());
                    break;
            }
            System.out.println(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
