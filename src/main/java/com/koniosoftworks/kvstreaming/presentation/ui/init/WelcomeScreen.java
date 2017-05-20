package com.koniosoftworks.kvstreaming.presentation.ui.init;

import com.koniosoftworks.kvstreaming.presentation.ui.BaseScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WelcomeScreen extends BaseScreen {
    @Override
    protected String getTitle() {
        return "KV-Welcome";
    }

    @Override
    protected String getResource() {
        return "/welcomeScreen.fxml";
    }
}
