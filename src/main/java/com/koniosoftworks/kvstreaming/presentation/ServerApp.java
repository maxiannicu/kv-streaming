package com.koniosoftworks.kvstreaming.presentation;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.koniosoftworks.kvstreaming.domain.server.Server;
import com.koniosoftworks.kvstreaming.presentation.di.ServerModule;

/**
 * Created by nicu on 5/15/17.
 */
public class ServerApp {
    private final static int PORT = 31012;
    private final Server server;

    @Inject
    public ServerApp(Server server) {
        this.server = server;
    }

    public void start(){
        //TODO init UI here
        server.start(PORT);
    }

    public static void main(String[] args){
        Injector injector = Guice.createInjector(new ServerModule());
        ServerApp instance = injector.getInstance(ServerApp.class);
        instance.start();
    }
}
