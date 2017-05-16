package com.koniosoftworks.kvstreaming.data.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.koniosoftworks.kvstreaming.data.io.ScannerDeserealizer;
import com.koniosoftworks.kvstreaming.data.io.StreamSerializer;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.domain.client.ClientListener;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;

/**
 * Created by nicu on 5/15/17.
 */
public class ClientImpl implements Client {

    private Socket socket;
    private ObjectInputStream objectReader;
    private Serializer serializer;
    private Deserializer deserializer;

    @Override
    public void connect(ClientListener clientListener, String host, int port) {
        try {
            socket = new Socket(host, port);
            serializer = new StreamSerializer(socket.getOutputStream());
            deserializer = new ScannerDeserealizer(socket.getInputStream());

            clientListener.onConnect();
            while (true){
                while (!deserializer.hasNextInt()){}
                Packet packet = new Packet();
                packet.unserialize(deserializer);
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
