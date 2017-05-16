package com.koniosoftworks.kvstreaming.domain.dto.messages;

import com.koniosoftworks.kvstreaming.domain.dto.NetworkSerializable;
import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by nicu on 5/16/17.
 */
public class InitializationMessage implements NetworkSerializable {
    private int udpPort;
    private String username;

    public InitializationMessage(int udpPort, String username) {
        this.udpPort = udpPort;
        this.username = username;
    }

    public InitializationMessage() {
    }

    public int getUdpPort() {
        return udpPort;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "InitializationMessage{" +
                "udpPort=" + udpPort +
                ", username='" + username + '\'' +
                '}';
    }


    @Override
    public void serialize(Serializer serializer) {
        serializer.put(udpPort);
        serializer.put(username);
    }

    @Override
    public void unserialize(Deserializer deserializer) {
        udpPort = deserializer.nextInt();
        username = deserializer.nextString();
    }

}
