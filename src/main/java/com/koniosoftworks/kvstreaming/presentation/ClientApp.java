package com.koniosoftworks.kvstreaming.presentation;

import java.awt.image.BufferedImage;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.domain.client.ClientListener;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.presentation.di.ClientModule;
import org.apache.logging.log4j.Logger;

/**
 * Created by nicu on 5/15/17.
 */
public class ClientApp implements ClientListener {
    private final Client client;

    @Inject
    public ClientApp(Client client) {
        this.client = client;
    }

    public void start(){
        //TODO init UI here
        client.connect(this, "localhost", 31012);
    }

    public static void main(String[] args){
        Injector injector = Guice.createInjector(new ClientModule());
        ClientApp instance = injector.getInstance(ClientApp.class);
        instance.start();
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect() {

    }

    @Override
    public void onConnectionFailed(String message) {

    }

    @Override
    public void onAccepted(String username) {

    }

    @Override
    public void onNewMessage(ChatMessage chatMessage) {

    }

    @Override
    public void onImageReceived(BufferedImage image) {

    }
}
