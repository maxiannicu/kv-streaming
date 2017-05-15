package com.koniosoftworks.kvstreaming.domain.client;


import com.koniosoftworks.kvstreaming.domain.dto.Message;

/**
 * Created by nicu on 5/15/17.
 */
public interface ClientListener {
    void onConnect();
    void onDisconnect();
    void onConnectionFailed(String message);
    void onAccepted(String username);
    void onNewMessage(Message message);
    void onImageReceived(Object object); //TODO: should be changed parameter data type
}
