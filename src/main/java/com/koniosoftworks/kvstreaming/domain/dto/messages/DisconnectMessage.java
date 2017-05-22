package com.koniosoftworks.kvstreaming.domain.dto.messages;

/**
 * Created by max on 5/20/17.
 */
public class DisconnectMessage {
    private String message;

    public DisconnectMessage(String message) {
        this.message = message;
    }

    public DisconnectMessage(){
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DisconnectMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
