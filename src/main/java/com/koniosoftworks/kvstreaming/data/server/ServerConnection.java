package com.koniosoftworks.kvstreaming.data.server;

import java.net.Socket;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class ServerConnection {
    private final Socket socket;
    private String username;

    public ServerConnection(Socket socket) {
        this.socket = socket;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
