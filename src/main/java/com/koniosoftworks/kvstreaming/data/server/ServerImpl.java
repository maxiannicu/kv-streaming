package com.koniosoftworks.kvstreaming.data.server;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.props.ServerProperties;
import com.koniosoftworks.kvstreaming.domain.server.Server;
import com.koniosoftworks.kvstreaming.presentation.di.SocketModule;
import com.koniosoftworks.kvstreaming.utils.NameGenerator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nicu on 5/15/17.
 */
public class ServerImpl implements Server {
    private final TaskScheduler taskScheduler;
    private final Set<ClientConnection> connections = new HashSet<>();
    private ServerSocket serverSocket;

    @Inject
    public ServerImpl(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(180000);
            taskScheduler.run(this::waitForConnections);
            System.out.println("Server started on port " + port);
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

    private void waitForConnections() {
        System.out.println("Waiting for connections");
        while (true) {
            if (connections.size() < ServerProperties.MAX_CONNECTIONS_ALLOWED - 1) {
                try {
                    Socket accept = serverSocket.accept();
                    SocketModule socketModule = new SocketModule(accept);
                    Injector injector = Guice.createInjector(socketModule);
                    ClientConnection instance = injector.getInstance(ClientConnection.class);
                    System.out.println("Client connected");
                    connections.add(instance);
                    onClientConnected(instance);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onClientConnected(ClientConnection connection) {
        Packet<InitializationMessage> message = new Packet<>(PacketType.INITITIALIZATION, new InitializationMessage(serverSocket.getLocalPort(), NameGenerator.generateName()));

        try {
            connection.send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onConnectionClosed(ClientConnection connection) {
        connections.remove(connection);
        //todo send message to chat or whetever
    }
}
