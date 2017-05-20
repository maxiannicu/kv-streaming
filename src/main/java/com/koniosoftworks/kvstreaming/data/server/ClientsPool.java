package com.koniosoftworks.kvstreaming.data.server;

import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessageRequest;
import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.utils.NameGenerator;
import com.sun.istack.internal.Nullable;

import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by max on 5/20/17.
 */
public class ClientsPool {
    private final PacketSerialization packetSerialization;
    private final EncodingAlgorithm encodingAlgorithm;
    private final Set<ClientConnection> connections;
    private final List<ChatMessage> chatMessages;

    @Inject
    public ClientsPool(TaskScheduler taskScheduler,
                       PacketSerialization packetSerialization,
                       EncodingAlgorithm encodingAlgorithm) {
        this.connections = new HashSet<>();
        this.chatMessages = new ArrayList<>();
        this.packetSerialization = packetSerialization;
        this.encodingAlgorithm = encodingAlgorithm;

        taskScheduler.schedule(this::checkForMessageRequest, 100, TimeUnit.MILLISECONDS);
    }

    @Nullable
    void addNewClient(Socket socket, int udpPort) {
        ClientConnection clientConnection = null;
        try {
            clientConnection = new ClientConnection(socket, packetSerialization, encodingAlgorithm);
            setupAndOpenConnection(udpPort, clientConnection);
            connections.add(clientConnection);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO log here exception.
        }
    }
    private void setupAndOpenConnection(int udpPort,  ClientConnection clientConnection) {
        clientConnection.setUsername(NameGenerator.generateName());
        clientConnection.setUdpPort(udpPort);
        clientConnection.open();
        chatMessages.forEach(clientConnection::sendMessage);
    }

    private void checkForMessageRequest() {
        for (ClientConnection connection : connections) {
            if (connection.hasReceivedPacket()) {
                try {
                    Packet packet = connection.getPacket();

                    if (packet.getPacketType() == PacketType.CHAT_MESSAGE_REQUEST) {
                        ChatMessageRequest data = (ChatMessageRequest) packet.getData();
                        dispatchMessage(connection, data);
                    }
                } catch (UnserializeException e) {
                    e.printStackTrace();
                    //TODO logo here exception.
                }
            }
        }
    }

    private void dispatchMessage(ClientConnection senderConnection, ChatMessageRequest data) {
        ChatMessage chatMessage = getChatMessage(senderConnection, data);
        connections.forEach(connection -> connection.sendMessage(chatMessage));
        chatMessages.add(chatMessage);
    }

    private ChatMessage getChatMessage(ClientConnection connection, ChatMessageRequest chatMessageRequest){
        return new ChatMessage(connection.getUsername(), chatMessageRequest.getMessage(), new Date());
    }

    int getSize() {
        return connections.size();
    }

    public void dropConnections() {
        connections.forEach(ClientConnection::drop);
        connections.clear();
    }
}
