package com.koniosoftworks.kvstreaming.domain.client;

import java.io.IOException;

/**
 * Created by nicu on 5/15/17.
 */
public interface Client {
    void connect(ClientListener clientListener, String host, int port) throws IOException;
    void disconnect();
    void sendMessage(String message);

    String getUsername();
}
