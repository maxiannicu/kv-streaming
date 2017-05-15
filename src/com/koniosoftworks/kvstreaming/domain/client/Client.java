package com.koniosoftworks.kvstreaming.domain.client;

/**
 * Created by nicu on 5/15/17.
 */
public interface Client {
    public void connect(ClientListener clientListener,String host,int port);

    public void disconnect();
}
