package com.koniosoftworks.kvstreaming.data.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.domain.client.ClientListener;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.props.MessagingProperties;

/**
 * Created by nicu on 5/15/17.
 */
public class ClientImpl implements Client {
    private Socket socket;
    private final PacketSerialization packetSerialization;
    private final EncodingAlgorithm encodingAlgorithm;
    private Scanner scanner;

    @Inject
    public ClientImpl(PacketSerialization packetSerialization, EncodingAlgorithm encodingAlgorithm) {
        this.packetSerialization = packetSerialization;
        this.encodingAlgorithm = encodingAlgorithm;
    }

    @Override
    public void connect(ClientListener clientListener, String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to server");
            clientListener.onConnect();
            scanner = new Scanner(socket.getInputStream());
            scanner.useDelimiter(MessagingProperties.MESSAGING_DELIMITER);
            while (!socket.isClosed()){
                while (!scanner.hasNext()){}

                String next = scanner.next();
                byte[] bytes = encodingAlgorithm.decode(next.getBytes());
                Packet packet = packetSerialization.unserialize(bytes);
                System.out.println(packet);
            }
        } catch (IOException e) {
            clientListener.onConnectionFailed(e.toString());
            e.printStackTrace();
        }
    }


    @Override
    public void disconnect() {

    }
}
