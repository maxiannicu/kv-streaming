package com.koniosoftworks.kvstreaming.presentation;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.presentation.di.ClientModule;
import sample.Main;

/**
 * Created by nicu on 5/15/17.
 */
public class ClientApp {

    private final Client client;

    @Inject
    public ClientApp(Client client) {
        this.client = client;
    }

    public void start(){
        //TODO init UI here
    }

    public static void main(String[] args){
        Injector injector = Guice.createInjector(new ClientModule());
        ClientApp instance = injector.getInstance(ClientApp.class);
        instance.start();
    }
}
