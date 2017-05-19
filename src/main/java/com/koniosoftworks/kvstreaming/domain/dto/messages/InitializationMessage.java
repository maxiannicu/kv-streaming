package com.koniosoftworks.kvstreaming.domain.dto.messages;

/**
 * Created by nicu on 5/16/17.
 */
public class InitializationMessage {
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

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "InitializationMessage{" +
                "udpPort=" + udpPort +
                ", username='" + username + '\'' +
                '}';
    }
}
