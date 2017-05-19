package com.koniosoftworks.kvstreaming.data.server;

import com.koniosoftworks.kvstreaming.data.io.ScannerStreamReader;
import com.koniosoftworks.kvstreaming.data.io.SimpleStreamWriter;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import com.koniosoftworks.kvstreaming.domain.io.StreamReader;
import com.koniosoftworks.kvstreaming.domain.io.StreamWriter;
import com.koniosoftworks.kvstreaming.domain.props.MessagingProperties;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class ClientConnection {
    private final Socket socket;
    private final PacketSerialization packetSerialization;
    private final EncodingAlgorithm encodingAlgorithm;
    private final StreamReader streamReader;
    private final StreamWriter streamWriter;
    private String username;

    public ClientConnection(Socket socket, PacketSerialization packetSerialization, EncodingAlgorithm encodingAlgorithm) throws IOException {
        this.socket = socket;
        this.packetSerialization = packetSerialization;
        this.encodingAlgorithm = encodingAlgorithm;
        this.streamReader = new ScannerStreamReader(socket.getInputStream());
        this.streamWriter = new SimpleStreamWriter(socket.getOutputStream());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void send(Packet packet) throws IOException {
        byte[] bytes = encodingAlgorithm.encode(packetSerialization.serialize(packet));
        streamWriter.put(new String(bytes));
    }

    public boolean hasReceivedPacket(){
        return streamReader.hasNextString();
    }

    public Packet getPacket() throws UnserializeException {
        if(!hasReceivedPacket())
            throw new UnserializeException("No message to deserialize");

        byte[] bytes = encodingAlgorithm.decode(streamReader.nextString().getBytes());
        try {
            return packetSerialization.unserialize(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read packet");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientConnection that = (ClientConnection) o;

        if (socket != null ? !socket.equals(that.socket) : that.socket != null) return false;
        return username != null ? username.equals(that.username) : that.username == null;
    }

    @Override
    public int hashCode() {
        int result = socket != null ? socket.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
