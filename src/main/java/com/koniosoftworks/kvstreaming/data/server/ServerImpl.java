package com.koniosoftworks.kvstreaming.data.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.data.io.ScannerDeserealizer;
import com.koniosoftworks.kvstreaming.data.io.StreamSerializer;
import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;
import com.koniosoftworks.kvstreaming.domain.props.ServerProperties;
import com.koniosoftworks.kvstreaming.domain.server.Server;
import com.koniosoftworks.kvstreaming.utils.NameGenerator;

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
        while (true){
            if(connections.size() < ServerProperties.MAX_CONNECTIONS_ALLOWED-1) {
                try {
                    ClientConnection clientConnection = new ClientConnection(serverSocket.accept());
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
