package com.koniosoftworks.kvstreaming.presentation.ui.client;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.client.Client;
import com.koniosoftworks.kvstreaming.domain.client.ClientListener;
import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.DisconnectMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.presentation.ui.BaseController;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.TextAlignment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by lschidu on 5/18/17.
 */
public class ClientScreenController extends BaseController implements ClientListener {
    private final Client client;
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private String usernameValue;

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
    private ListView<StackPane> listView;
    @FXML
    private Label username;
    @FXML
    private ImageView imageView;

    @Inject
    public ClientScreenController(Client client) {
        this.client = client;
    }

    public void initialize() {
        setButtonsState(false);

        listView.getItems().addListener(new ListChangeListener<StackPane>() {
            @Override
            public void onChanged(Change<? extends StackPane> c) {
                listView.scrollTo(c.getList().size() - 1);
            }
        });
    }

    public void handleConnectButton(ActionEvent actionEvent) {
        String[] split = ipPort.getText().split(":");
        try {
            this.client.connect(this, split[0], Integer.valueOf(split[1]));
            setButtonsState(true);
        } catch (Exception ex) {
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

    public void handleTextMessageKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleSendButton(null);
        }
    }

    @Override
    public void onConnect() {
        //TODO add logic here.
    }

    @Override
    public void onDisconnect(DisconnectMessage disconnectMessage) {
        Platform.runLater(() -> {
            setButtonsState(false);
            listView.setItems(null);
            showInfoAlert("Information Dialog", "Server", String.format("%s", disconnectMessage.getMessage()));
        });
    }

    @Override
    public void onConnectionFailed(String message) {
        //TODO add logic here.
    }

    @Override
    public void onInitializationMessage(InitializationMessage initializationMessage) {
        Platform.runLater(() -> {
            usernameValue = initializationMessage.getUsername();
            username.setText(usernameValue);
            showInfoAlert("Information Dialog", "Server", String.format("You was accepted with name %s", usernameValue));
        });
    }

    private void showInfoAlert(String title, String server, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(server);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void onChatMessage(ChatMessage chatMessage) {
        if (!chatMessage.getSender().equals(client.getUsername())) {
            AudioClip audioClip = new AudioClip(this.getClass().getResource("/sound/new-message.wav").toExternalForm());
            audioClip.play();
        }
        Platform.runLater(() -> appendMessage(chatMessage));
    }

    private void appendMessage(ChatMessage chatMessage) {
        ObservableList<StackPane> messageCells = listView.getItems();

        StackPane messageCell = getNewCell(chatMessage.getSentOnUtc(), chatMessage.getMessage(), chatMessage.getSender());
        messageCells.add(messageCell);
        listView.setItems(messageCells);
    }

    private StackPane getNewCell(Date sentOnUtc, String message, String sender) {

        Label messageLabel = Objects.equals(sender, usernameValue) ? getUserMessageLabel(message, sender, sentOnUtc) : getSenderMessageLabel(message, sender, sentOnUtc);
        StackPane messageCell = new StackPane();
        messageCell.getChildren().add(messageLabel);
        messageCell.setAlignment(Objects.equals(sender, usernameValue) ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        return messageCell;
    }

    @Override
    public void onNewStreamingImage(Image image) {
        imageView.setImage(image);
    }

    private Label getSenderMessageLabel(String message, Object sender, Date sentOnUtc) {
        Label label = new Label(String.format("[%s] %s\n%s", dateFormat.format(sentOnUtc), sender, message));

        ComboBox<TextAlignment> textAlignmentBox = new ComboBox<>();
        textAlignmentBox.getItems().addAll(TextAlignment.values());
        textAlignmentBox.getSelectionModel().select(TextAlignment.LEFT);
        label.textAlignmentProperty().bind(textAlignmentBox.valueProperty());

        label.setAlignment(Pos.CENTER_LEFT);
        label.setMaxWidth(200);
        label.setWrapText(true);
        label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 12; -fx-text-fill: deepskyblue;");
        return label;
    }

    private Label getUserMessageLabel(String message, Object sender, Date sentOnUtc) {
        Label label = new Label(String.format("%s [%s]\n%s", sender, dateFormat.format(sentOnUtc), message));

        ComboBox<TextAlignment> textAlignmentBox = new ComboBox<>();
        textAlignmentBox.getItems().addAll(TextAlignment.values());
        textAlignmentBox.getSelectionModel().select(TextAlignment.RIGHT);
        label.textAlignmentProperty().bind(textAlignmentBox.valueProperty());


        label.setAlignment(Pos.CENTER_RIGHT);
        label.setMaxWidth(200);
        label.setWrapText(true);
        label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 12; -fx-text-fill: darkblue;");
        return label;
    }

    private void setButtonsState(boolean connected) {
        this.connectButton.setDisable(connected);
        this.disconnectButton.setDisable(!connected);
        this.messageTextField.setDisable(!connected);
        this.sendButton.setDisable(!connected);
        this.listView.setDisable(!connected);
    }

}
