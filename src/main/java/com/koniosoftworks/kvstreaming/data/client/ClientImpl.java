package com.koniosoftworks.kvstreaming.data.client;

import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.domain.client.ClientListener;
import com.koniosoftworks.kvstreaming.domain.dto.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by nicu on 5/15/17.
 */
public class ClientImpl implements Client {

    private Socket client;
    private ObjectInputStream objectReader;

    @Override
    public void connect(ClientListener clientListener, String host, int port) {
        try {
            client = new Socket(host, port);
            clientListener.onConnect();
            receiveUserNameAndPort();
            receiveObjectData();
        } catch (IOException e) {
            clientListener.onConnectionFailed(e.toString());
            e.printStackTrace();
        }
    }

    private void receiveUserNameAndPort() {
        try {
            objectReader = new ObjectInputStream(client.getInputStream());
            System.out.println("Message from Server " + objectReader.readInt() + " " + objectReader.readUTF()); //TODO : For testing purposes
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void receiveObjectData() {

        try {
            Message message = (Message) objectReader.readObject();
            System.out.println("Object from server " + message.getMessage() + " " + message.getSender() + " " + message.getSentOnUtc().toString()); //TODO : For testing purposes
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void disconnect() {

    }
}
