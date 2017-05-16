package sample;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.SampleServer;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        SampleServer server = new SampleServer(6066);
        server.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
