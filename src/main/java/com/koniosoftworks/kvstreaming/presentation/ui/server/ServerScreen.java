package com.koniosoftworks.kvstreaming.presentation.ui.server;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.server.Server;
import com.koniosoftworks.kvstreaming.presentation.ui.BaseScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerScreen extends BaseScreen {
    @Override
    protected String getTitle() {
        return "KV-Server";
    }

    @Override
    protected String getResource() {
        return "/serverScreen.fxml";
    }
}
