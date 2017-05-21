package com.koniosoftworks.kvstreaming.presentation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.koniosoftworks.kvstreaming.presentation.di.AppModule;
import com.koniosoftworks.kvstreaming.presentation.ui.init.WelcomeScreen;
import com.sun.deploy.uitoolkit.ui.ConsoleController;
import com.sun.xml.internal.fastinfoset.sax.Properties;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by nicu on 5/20/17.
 */
public class App {
    private static Injector injector;

    public static Injector getInjector() {
        return injector;
    }

    public static void main(String[] args) {
        injector = Guice.createInjector(new AppModule());
        Application.launch(WelcomeScreen.class, args);
    }

}
