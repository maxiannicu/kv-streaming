package com.koniosoftworks.kvstreaming.presentation.di;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.koniosoftworks.kvstreaming.data.client.ClientImpl;
import com.koniosoftworks.kvstreaming.domain.client.Client;

/**
 * Created by nicu on 5/15/17.
 */
public class ClientModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(Client.class).to(ClientImpl.class).in(Scopes.SINGLETON);
    }
}
