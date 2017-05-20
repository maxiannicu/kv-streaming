package com.koniosoftworks.kvstreaming.presentation.ui.client;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.client.Client;

/**
 * Created by lschidu on 5/18/17.
 */
public class ClientScreenController {
    private final Client client;

    @Inject
    public ClientScreenController(Client client) {
        this.client = client;
    }
}
