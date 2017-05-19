package com.koniosoftworks.kvstreaming.data.server;

import com.koniosoftworks.kvstreaming.data.io.ScannerDeserealizer;
import com.koniosoftworks.kvstreaming.data.io.StreamSerializer;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;
import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class ClientConnection {
    private final Socket socket;
    private final Serializer serializer;
    private final Deserializer deserializer;
    private String username;

    public ClientConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.serializer = new StreamSerializer(socket.getOutputStream());
        this.deserializer = new ScannerDeserealizer(socket.getInputStream());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void send(Packet packet) throws IOException {
        packet.serialize(serializer);
        socket.getOutputStream().flush();
    }

    public boolean hasReceivedPacket(){
        return deserializer.hasNextInt();
    }

    public Packet getPacket() throws UnserializeException {
        if(!hasReceivedPacket())
            throw new UnserializeException("No message to unserialize");
        Packet packet = new Packet();
        packet.unserialize(deserializer);

        return packet;
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
