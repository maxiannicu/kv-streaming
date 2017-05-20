package com.koniosoftworks.kvstreaming.presentation.ui.client;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.presentation.ui.BaseScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientScreen extends BaseScreen {

    @Override
    protected String getResource() {
        return "/clientScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "KV-Client";
    }
}
