package com.koniosoftworks.kvstreaming.presentation.ui.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientScreen extends Application {

    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;

    private static ClientScreen INSTANCE = new ClientScreen();


    public static ClientScreen getINSTANCE() {
        return INSTANCE;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/clientScreen.fxml"));
        primaryStage.setTitle("Konio Video Streaming");
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("/styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
