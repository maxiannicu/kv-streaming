package com.koniosoftworks.kvstreaming.presentation.ui.client;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.domain.client.ClientListener;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.DisconnectMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.presentation.ui.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by lschidu on 5/18/17.
 */
public class ClientScreenController extends BaseController implements ClientListener {
    private final Client client;
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @FXML
    private Button connectButton;
    @FXML
    private Button disconnectButton;
    @FXML
    private Button sendButton;
    @FXML
    private TextField messageTextField;
    @FXML
    private TextField ipPort;
    @FXML
    private TextArea textArea;

    @Inject
    public ClientScreenController(Client client) {
        this.client = client;
    }

    public void initialize(){
        setButtonsState(false);
    }

    public void handleConnectButton(ActionEvent actionEvent) {
        String[] split = ipPort.getText().split(":");
        try {
            this.client.connect(this, split[0], Integer.valueOf(split[1]));
            setButtonsState(true);
        } catch (Exception ex){
            showException(ex);
        }
    }

    public void handleDisconnectButton(ActionEvent actionEvent) {
        setButtonsState(false);
    }

    public void handleSendButton(ActionEvent actionEvent) {
        client.sendMessage(messageTextField.getText());
        messageTextField.setText("");
    }

    public void handleTextMessageKeyEvent(KeyEvent keyEvent){
        if (keyEvent.getCode() == KeyCode.ENTER){
            handleSendButton(null);
        }
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect(DisconnectMessage disconnectMessage) {
        textArea.setText(String.format("%s", disconnectMessage.getMessage()));
    }

    @Override
    public void onConnectionFailed(String message) {

    }

    @Override
    public void onInitializationMessage(InitializationMessage initializationMessage) {
        appendLine(String.format("Server: You was accepted with name %s", initializationMessage.getUsername()));
    }

    @Override
    public void onChatMessage(ChatMessage chatMessage) {
        if(!chatMessage.getSender().equals(client.getUsername())){
            AudioClip audioClip = new AudioClip(this.getClass().getResource("/sound/new-message.wav").toExternalForm());
            audioClip.play();
        }

        appendLine(String.format("[%s]%s : %s", dateFormat.format(chatMessage.getSentOnUtc()), chatMessage.getSender(), chatMessage.getMessage()));
    }

    private void appendLine(String line) {
        String text = textArea.getText();
        textArea.setText(String.format("%s\n%s", text, line));
    }

    private void setButtonsState(boolean connected) {
        this.connectButton.setDisable(connected);
        this.disconnectButton.setDisable(!connected);
        this.messageTextField.setDisable(!connected);
        this.sendButton.setDisable(!connected);
        this.textArea.setDisable(!connected);
    }

}
