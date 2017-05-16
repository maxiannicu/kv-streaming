package com.koniosoftworks.kvstreaming.presentation.di;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.koniosoftworks.kvstreaming.data.server.ServerImpl;
import com.koniosoftworks.kvstreaming.domain.server.Server;

/**
 * Created by nicu on 5/15/17.
 */
public class ServerModule extends BaseModule {
    @Override
    public void configure(Binder binder) {
        super.configure(binder);
        binder.bind(Server.class).to(ServerImpl.class).in(Scopes.SINGLETON);
    }
}
