package com.koniosoftworks.kvstreaming.presentation.ui.init;

import com.koniosoftworks.kvstreaming.presentation.ui.client.ClientScreen;
import com.koniosoftworks.kvstreaming.presentation.ui.server.ServerScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by lschidu on 5/18/17.
 */
public class WelcomeScreenController {

    @FXML private Button startClientButton;
    @FXML private Button startServerButton;


    public void handleStartClient(ActionEvent actionEvent) {
        Stage stage = (Stage) startClientButton.getScene().getWindow();
        try {
            ClientScreen.getINSTANCE().start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleStartServer(ActionEvent actionEvent) {
        Stage stage = (Stage) startServerButton.getScene().getWindow();
        try {
            ServerScreen.getINSTANCE().start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
