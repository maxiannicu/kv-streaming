package com.koniosoftworks.kvstreaming.domain.client;


import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.DisconnectMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import javafx.scene.image.Image;

/**
 * Created by nicu on 5/15/17.
 */
public interface ClientListener {
    void onConnect();
    void onDisconnect(DisconnectMessage disconectMessage);
    void onConnectionFailed(String message);
    void onInitializationMessage(InitializationMessage initializationMessage);
    void onChatMessage(ChatMessage chatMessage);
    void onNewStreamingImage(Image image);
}
