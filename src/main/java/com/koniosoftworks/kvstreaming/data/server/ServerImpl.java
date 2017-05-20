package com.koniosoftworks.kvstreaming.data.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.props.ServerProperties;
import com.koniosoftworks.kvstreaming.domain.server.Server;
import com.koniosoftworks.kvstreaming.utils.NameGenerator;
import org.apache.logging.log4j.Logger;

/**
 * Created by nicu on 5/15/17.
 */
public class ServerImpl implements Server {
    private final TaskScheduler taskScheduler;
    private final PacketSerialization packetSerialization;
    private final EncodingAlgorithm encodingAlgorithm;
    private final Logger logger;

    private final Set<ClientConnection> connections = new HashSet<>();
    private ServerSocket serverSocket;

    @Inject
    public ServerImpl(TaskScheduler taskScheduler, PacketSerialization packetSerialization, EncodingAlgorithm encodingAlgorithm, Logger logger) {
        this.taskScheduler = taskScheduler;
        this.packetSerialization = packetSerialization;
        this.encodingAlgorithm = encodingAlgorithm;
        this.logger = logger;
    }

    @Override
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server started at "+serverSocket.getInetAddress().toString()+":"+serverSocket.getLocalPort());
            serverSocket.setSoTimeout(180000);
            taskScheduler.run(this::waitForConnections);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            serverSocket.close();
            taskScheduler.stopAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnections(){
        System.out.println("Waiting for connections");
        while (true){
            if(connections.size() < ServerProperties.MAX_CONNECTIONS_ALLOWED-1) {
                try {
                    ClientConnection clientConnection = new ClientConnection(serverSocket.accept(), packetSerialization,encodingAlgorithm);
                    System.out.println("Client connected");
                    connections.add(clientConnection);
                    onClientConnected(clientConnection);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onClientConnected(ClientConnection connection) {
        Packet<InitializationMessage> message = new Packet<>(PacketType.INITITIALIZATION, new InitializationMessage(serverSocket.getLocalPort(),NameGenerator.generateName()));

        try {
            connection.send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onConnectionClosed(ClientConnection connection){
        connections.remove(connection);
        //todo send message to chat or whetever
    }
}
