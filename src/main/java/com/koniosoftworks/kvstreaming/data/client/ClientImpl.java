package com.koniosoftworks.kvstreaming.data.client;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.data.io.ScannerStreamReader;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.domain.client.ClientListener;
import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.core.MessageBus;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.io.StreamReader;

/**
 * Created by nicu on 5/15/17.
 */
public class ClientImpl implements Client {
    private Socket socket;
    private final PacketSerialization packetSerialization;
    private final EncodingAlgorithm encodingAlgorithm;
    private final TaskScheduler taskScheduler;
    private final MessageBus messageBus;
    private StreamReader streamReader;

    @Inject
    public ClientImpl(PacketSerialization packetSerialization, EncodingAlgorithm encodingAlgorithm, TaskScheduler taskScheduler, MessageBus messageBus) {
        this.packetSerialization = packetSerialization;
        this.encodingAlgorithm = encodingAlgorithm;
        this.taskScheduler = taskScheduler;
        this.messageBus = messageBus;
    }

    @Override
    public void connect(ClientListener clientListener, String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to server");
            clientListener.onConnect();
            streamReader = new ScannerStreamReader(socket.getInputStream());
            taskScheduler.schedule(this::checkMessage,100, TimeUnit.MILLISECONDS);
        } catch (IOException e) {
            clientListener.onConnectionFailed(e.toString());
            e.printStackTrace();
        }
    }


    @Override
    public void disconnect() {

    }

    private void checkMessage(){
        if (socket.isClosed())
            taskScheduler.unschedule(this::checkMessage);
        if (!streamReader.hasNextString())
            return;

        byte[] bytes = encodingAlgorithm.decode(streamReader.nextString().getBytes());
        Packet packet = null;
        try {
            packet = packetSerialization.unserialize(bytes);
            messageBus.post(packet.getData());
            System.out.println(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
