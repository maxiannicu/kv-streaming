package com.koniosoftworks.kvstreaming.presentation.ui.server;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.server.Server;

/**
 * Created by lschidu on 5/18/17.
 */
public class ServerScreenController {
    private final Server server;

    @Inject
    public ServerScreenController(Server server) {
        this.server = server;
    }
}
