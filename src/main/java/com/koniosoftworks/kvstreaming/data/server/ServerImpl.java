package com.koniosoftworks.kvstreaming.data.server;

import com.koniosoftworks.kvstreaming.domain.dto.Message;
import com.koniosoftworks.kvstreaming.domain.server.Server;
import com.koniosoftworks.kvstreaming.utils.NameGenerator;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.Date;

/**
 * Created by nicu on 5/15/17.
 */
public class ServerImpl implements Server {

    private static final int UDP_PORT = 9000;
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectOutputStream objectWriter;

    @Override
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(180000);
            System.out.println("Waiting for connections...");
            socket = serverSocket.accept();
            sendUserNameAndPort();
            sendObjectData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendUserNameAndPort() {

        try {
            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectWriter.writeInt(UDP_PORT);
            objectWriter.flush();
            objectWriter.writeUTF(NameGenerator.generateName());
            objectWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendObjectData() {
        try {
            objectWriter.writeObject(new Message("localhost", "Dummy Message", Date.from(Instant.now())));
            objectWriter.flush();
            objectWriter.close();
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
