package com.koniosoftworks.kvstreaming.data.server;

import com.koniosoftworks.kvstreaming.data.io.ScannerDeserealizer;
import com.koniosoftworks.kvstreaming.data.io.StreamSerializer;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;
import com.koniosoftworks.kvstreaming.domain.server.Server;
import com.koniosoftworks.kvstreaming.utils.NameGenerator;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Created by nicu on 5/15/17.
 */
public class ServerImpl implements Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private Serializer serializer;
    private Deserializer deserializer;

    @Override
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(180000);
            System.out.println("Waiting for connections...");
            socket = serverSocket.accept();
            serializer = new StreamSerializer(socket.getOutputStream());
            deserializer = new ScannerDeserealizer(socket.getInputStream());
            sendUserNameAndPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendUserNameAndPort() {
        Packet<InitializationMessage> message = new Packet<>(PacketType.INITITIALIZATION, new InitializationMessage(serverSocket.getLocalPort(),NameGenerator.generateName()));

        message.serialize(serializer);
        try {
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            serverSocket.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
