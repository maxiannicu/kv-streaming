package com.koniosoftworks.kvstreaming.data.server;

import com.koniosoftworks.kvstreaming.data.core.Connection;
import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessageRequest;
import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.utils.NameGenerator;
import com.sun.istack.internal.Nullable;

import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by max on 5/20/17.
 */
public class ClientsPool {
    private final PacketSerialization packetSerialization;
    private final EncodingAlgorithm encodingAlgorithm;
    private final Set<ClientConnection> connections;
    private final Set<ChatMessageRequest> chatMessageRequests;


    @Inject
    public ClientsPool(TaskScheduler taskScheduler,
                       PacketSerialization packetSerialization,
                       EncodingAlgorithm encodingAlgorithm) {
        this.connections = new HashSet<>();
        this.chatMessageRequests = new HashSet<>();
        this.packetSerialization = packetSerialization;
        this.encodingAlgorithm = encodingAlgorithm;

        taskScheduler.schedule(this::checkForMessageRequest, 100, TimeUnit.MILLISECONDS);
    }

    @Nullable
    void addNewClient(Socket socket) {
        ClientConnection clientConnection = null;
        try {
            clientConnection = new ClientConnection(socket, packetSerialization, encodingAlgorithm);
            setupAndOpenConnection(socket, clientConnection);
            connections.add(clientConnection);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO log here exception.
        }
    }

    private void setupAndOpenConnection(Socket socket, ClientConnection clientConnection) {
        clientConnection.setUsername(NameGenerator.generateName());
        clientConnection.setUdpPort(socket.getLocalPort());
        clientConnection.open();
        chatMessageRequests.forEach(clientConnection::sendMessage);
    }

    private void checkForMessageRequest(){
        for (Connection connection : connections) {
            if (connection.hasReceivedPacket()) {
                try {
                    Packet packet = connection.getPacket();

                    if (packet.getPacketType() == PacketType.CHAT_MESSAGE_REQUEST) {
                        ChatMessageRequest data = (ChatMessageRequest) packet.getData();
                        dispatchMessage(data);
                    }
                } catch (UnserializeException e) {
                    e.printStackTrace();
                    //TODO logo here exception.
                }
            }
        }
    }

    private void dispatchMessage(ChatMessageRequest data) {
        connections.forEach(connection -> connection.sendMessage(data));
        chatMessageRequests.add(data);
    }

    int getSize() {
        return connections.size();
    }

    public void dropConnections() {
        connections.forEach(ClientConnection::drop);
        connections.clear();
    }
}
