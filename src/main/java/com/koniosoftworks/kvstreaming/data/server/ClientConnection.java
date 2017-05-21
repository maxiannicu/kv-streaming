package com.koniosoftworks.kvstreaming.data.server;

import com.koniosoftworks.kvstreaming.data.core.Connection;
import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.DisconnectMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.PacketSerialization;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */

class ClientConnection extends Connection {
    private String username;

    ClientConnection(Socket socket, PacketSerialization packetSerialization, EncodingAlgorithm encodingAlgorithm, Logger logger) throws IOException {
        super(socket, packetSerialization, encodingAlgorithm, logger);
    }

    void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    void sendMessage(ChatMessage chatMessage) {
        Packet<ChatMessage> packet = new Packet<>(PacketType.CHAT_MESSAGE, chatMessage);
        try {
            send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO handle here exception.
        }
    }

    void open() {
        Packet<InitializationMessage> packet = new Packet<>(PacketType.INITIALIZATION,
                new InitializationMessage(username));

        try {
            send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO handle here exception.
        }
    }

    void drop() {
        Packet<DisconnectMessage> message = new Packet<>(PacketType.DISCONNECT,
                new DisconnectMessage("Server was closed"));
        try {
            send(message);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO handle here exception.
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
