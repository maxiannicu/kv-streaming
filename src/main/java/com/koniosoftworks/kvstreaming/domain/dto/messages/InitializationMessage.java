package com.koniosoftworks.kvstreaming.domain.dto.messages;

/**
 * Created by nicu on 5/16/17.
 */
public class InitializationMessage {
    private String username;
    private int udpPort;

    public InitializationMessage(String username, int udpPort) {
        this.username = username;
        this.udpPort = udpPort;
    }

    public InitializationMessage() {
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    @Override
    public String toString() {
        return "InitializationMessage{" +
                "username='" + username + '\'' +
                ", udpPort=" + udpPort +
                '}';
    }
}
