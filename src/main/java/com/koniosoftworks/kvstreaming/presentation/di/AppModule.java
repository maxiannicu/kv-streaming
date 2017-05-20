package com.koniosoftworks.kvstreaming.presentation.di;

import com.google.inject.Binder;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.koniosoftworks.kvstreaming.data.client.ClientImpl;
import com.koniosoftworks.kvstreaming.data.server.ServerImpl;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.domain.server.Server;
import com.koniosoftworks.kvstreaming.presentation.ui.client.ClientScreen;
import com.koniosoftworks.kvstreaming.presentation.ui.client.ClientScreenController;
import com.koniosoftworks.kvstreaming.presentation.ui.init.WelcomeScreen;
import com.koniosoftworks.kvstreaming.presentation.ui.init.WelcomeScreenController;
import com.koniosoftworks.kvstreaming.presentation.ui.server.ServerScreen;
import com.koniosoftworks.kvstreaming.presentation.ui.server.ServerScreenController;
import javafx.application.Application;

import javax.security.auth.callback.Callback;

/**
 * Created by nicu on 5/20/17.
 */
public class AppModule extends BaseModule {
    @Override
    public void configure(Binder binder) {
        super.configure(binder);

        binder.bind(Client.class).to(ClientImpl.class).in(Scopes.SINGLETON);
        binder.bind(Server.class).to(ServerImpl.class).in(Scopes.SINGLETON);
        binder.bind(ClientScreenController.class);
        binder.bind(ServerScreenController.class);
        binder.bind(WelcomeScreenController.class);
        binder.bind(Application.class).annotatedWith(Names.named("client")).to(ClientScreen.class);
        binder.bind(Application.class).annotatedWith(Names.named("server")).to(ServerScreen.class);
    }
}
