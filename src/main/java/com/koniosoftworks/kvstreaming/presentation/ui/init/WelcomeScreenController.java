package com.koniosoftworks.kvstreaming.presentation.ui.init;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.koniosoftworks.kvstreaming.presentation.ui.client.ClientScreen;
import com.koniosoftworks.kvstreaming.presentation.ui.server.ServerScreen;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by lschidu on 5/18/17.
 */
public class WelcomeScreenController {
    private final Application clientApplication;
    private final Application serverApplication;

    @FXML private Button startClientButton;
    @FXML private Button startServerButton;

    @Inject
    public WelcomeScreenController(@Named("client") Application clientApplication, @Named("server")Application serverApplication) {
        this.clientApplication = clientApplication;
        this.serverApplication = serverApplication;
    }


    public void handleStartClient(ActionEvent actionEvent) {
        Stage stage = (Stage) startClientButton.getScene().getWindow();
        try {
            clientApplication.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleStartServer(ActionEvent actionEvent) {
        Stage stage = (Stage) startServerButton.getScene().getWindow();
        try {
            serverApplication.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
