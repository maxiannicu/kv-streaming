package com.koniosoftworks.kvstreaming.presentation.ui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.koniosoftworks.kvstreaming.presentation.App;
import com.koniosoftworks.kvstreaming.presentation.di.AppModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Created by nicu on 5/20/17.
 */
public abstract class BaseScreen extends Application {
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = getParent();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("/styles.css");
        primaryStage.setTitle(getTitle());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private Parent getParent() throws java.io.IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getResource()), null, new JavaFXBuilderFactory(), App.getInjector()::getInstance);

        return fxmlLoader.load();
    }

    protected abstract String getTitle();

    protected abstract String getResource();
}
