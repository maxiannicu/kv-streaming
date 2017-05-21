package com.koniosoftworks.kvstreaming.data.server;

import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessageRequest;
import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.utils.Formatting;
import com.koniosoftworks.kvstreaming.utils.NameGenerator;
import com.sun.istack.internal.Nullable;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * Created by max on 5/20/17.
 */
public class ClientsPool {
    private final PacketSerialization packetSerialization;
    private final EncodingAlgorithm encodingAlgorithm;
    private final Set<ClientConnection> connections;
    private final List<ChatMessage> chatMessages;
    private final Logger logger;
    private final TaskScheduler taskScheduler;
    private final Set<Runnable> runningRunnables = new HashSet<>();

    @Inject
    public ClientsPool(
            PacketSerialization packetSerialization,
            EncodingAlgorithm encodingAlgorithm, Logger logger, TaskScheduler taskScheduler) {
        this.logger = logger;
        this.taskScheduler = taskScheduler;
        this.connections = new HashSet<>();
        this.chatMessages = new ArrayList<>();
        this.packetSerialization = packetSerialization;
        this.encodingAlgorithm = encodingAlgorithm;

    }

    @Nullable
    void addNewClient(Socket socket) {
        ClientConnection clientConnection = null;
        try {
            clientConnection = new ClientConnection(socket, packetSerialization, encodingAlgorithm, logger);
            setupAndOpenConnection(clientConnection);
            connections.add(clientConnection);
            logger.info("Client connected "+ Formatting.getConnectionInfo(socket));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void setupAndOpenConnection(ClientConnection clientConnection) {
        clientConnection.setUsername(NameGenerator.generateName());
        clientConnection.open();
        chatMessages.forEach(clientConnection::sendMessage);

        Runnable runnable = () -> checkForMessageRequest(clientConnection);
        runningRunnables.add(runnable);
        taskScheduler.run(runnable);
    }


    public void dropConnections() {
        logger.info("Droping all active connections");
        connections.forEach(ClientConnection::drop);
        runningRunnables.forEach(taskScheduler::unschedule);
        connections.clear();
    }

    private void checkForMessageRequest(ClientConnection connection) {
        while (true) {
            if (connection.hasReceivedPacket()) {
                try {
                    Packet packet = connection.getPacket();
                    logger.debug(packet);

                    if (packet.getPacketType() == PacketType.CHAT_MESSAGE_REQUEST) {
                        ChatMessageRequest data = (ChatMessageRequest) packet.getData();
                        dispatchMessage(connection, data);
                    }
                } catch (UnserializeException e) {
                    logger.error(e);
                }
            }
        }
    }

    private void dispatchMessage(ClientConnection senderConnection, ChatMessageRequest data) {
        ChatMessage chatMessage = getChatMessage(senderConnection, data);
        connections.forEach(connection -> connection.sendMessage(chatMessage));
        chatMessages.add(chatMessage);
    }

    private ChatMessage getChatMessage(ClientConnection connection, ChatMessageRequest chatMessageRequest) {
        return new ChatMessage(connection.getUsername(), chatMessageRequest.getMessage(), new Date());
    }

    public int getSize() {
        return connections.size();
    }
}
