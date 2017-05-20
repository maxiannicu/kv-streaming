package com.koniosoftworks.kvstreaming.presentation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.koniosoftworks.kvstreaming.presentation.di.AppModule;
import com.koniosoftworks.kvstreaming.presentation.ui.init.WelcomeScreen;
import javafx.application.Application;

/**
 * Created by nicu on 5/20/17.
 */
public class App {
    private static Injector injector;

    public static Injector getInjector() {
        return injector;
    }

    public static void main(String[] args){
        injector = Guice.createInjector(new AppModule());
        Application.launch(WelcomeScreen.class,args);
    }
}
