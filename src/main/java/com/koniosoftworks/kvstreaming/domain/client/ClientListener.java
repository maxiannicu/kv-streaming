package com.koniosoftworks.kvstreaming.domain.client;


import java.awt.image.BufferedImage;

import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;

/**
 * Created by nicu on 5/15/17.
 */
public interface ClientListener {
    void onConnect();
    void onDisconnect();
    void onConnectionFailed(String message);
    void onInitializationMessage(InitializationMessage initializationMessage);
    void onChatMessage(ChatMessage chatMessage);
}
