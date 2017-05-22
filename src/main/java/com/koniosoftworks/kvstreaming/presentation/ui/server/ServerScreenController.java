package com.koniosoftworks.kvstreaming.presentation.ui.server;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.data.video.ScreenRecordAlgorithm;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessageRequest;
import com.koniosoftworks.kvstreaming.domain.server.Server;
import com.koniosoftworks.kvstreaming.presentation.ui.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.Logger;

/**
 * Created by lschidu on 5/18/17.
 */
public class ServerScreenController extends BaseController {
    private final Server server;
    private final Logger logger;
    @FXML private Button startButton;
    @FXML private Button stopButton;
    @FXML private TextField tcpPortTextField;
    @FXML private TextArea textArea;
    @FXML private Button startScreenRecordingButton;
    private boolean screnRecording = false;
    private boolean connected = false;

    @Inject
    public ServerScreenController(Server server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    public void initialize(){
        refreshButtonState();
    }

    public void handleStartButton(ActionEvent actionEvent) {
        this.server.start(Integer.valueOf(tcpPortTextField.getText()));
        connected = true;
        refreshButtonState();
    }

    public void handleStopButton(ActionEvent actionEvent) {
        this.server.stop();
        connected = false;
        refreshButtonState();
        screnRecording = false;
    }

    public void handleStartScreenRecordingButton(ActionEvent actionEvent){
        if(!screnRecording) {
            server.startStreaming(new ScreenRecordAlgorithm(logger));
            screnRecording = true;
        } else {
            server.stopStreaming();
            screnRecording = false;
        }
        refreshButtonState();
    }

    private void refreshButtonState() {
        this.startButton.setDisable(connected);
        this.stopButton.setDisable(!connected);
        this.startScreenRecordingButton.setText(screnRecording ? "Stop screen recording" : "Start screen recording");
        this.startScreenRecordingButton.setDisable(!connected);
    }
}
