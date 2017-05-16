package com.koniosoftworks.kvstreaming.domain.server;

/**
 * Created by nicu on 5/15/17.
 */
public interface Server {
    void start(int port);
    void stop();
}
