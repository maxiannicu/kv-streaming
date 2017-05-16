package com.koniosoftworks.kvstreaming.domain.client;


import java.awt.image.BufferedImage;

import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;

/**
 * Created by nicu on 5/15/17.
 */
public interface ClientListener {
    void onConnect();
    void onDisconnect();
    void onConnectionFailed(String message);
    void onAccepted(String username);
    void onNewMessage(ChatMessage chatMessage);
    void onImageReceived(BufferedImage image);
}
