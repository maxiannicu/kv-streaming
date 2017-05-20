package com.koniosoftworks.kvstreaming.presentation.ui.server;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessageRequest;
import com.koniosoftworks.kvstreaming.domain.server.Server;
import com.koniosoftworks.kvstreaming.presentation.ui.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Created by lschidu on 5/18/17.
 */
public class ServerScreenController extends BaseController {
    private final Server server;
    @FXML private Button startButton;
    @FXML private Button stopButton;
    @FXML private TextField tcpPortTextField;
    @FXML private TextArea textArea;

    @Inject
    public ServerScreenController(Server server) {
        this.server = server;
    }

    public void handleStartButton(ActionEvent actionEvent) {
        this.server.start(Integer.valueOf(tcpPortTextField.getText()));
        setButtonsState(true);
    }

    public void handleStopButton(ActionEvent actionEvent) {
        this.server.stop();
        setButtonsState(false);
    }

    //todo
    public void onChatMessageRequest(ChatMessageRequest request){
        String text = this.textArea.getText();

        this.textArea.setText(String.format("%s\nMessageRequest:%s",text,request.getMessage()));
    }

    private void setButtonsState(boolean connected) {
        this.startButton.setDisable(connected);
        this.stopButton.setDisable(!connected);
    }
}
