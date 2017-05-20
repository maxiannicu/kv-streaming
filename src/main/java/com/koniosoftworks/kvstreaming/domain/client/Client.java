package com.koniosoftworks.kvstreaming.domain.client;

/**
 * Created by nicu on 5/15/17.
 */
public interface Client {
    void connect(ClientListener clientListener, String host, int port);
    void disconnect();
    void sendMessage(String message);
}
