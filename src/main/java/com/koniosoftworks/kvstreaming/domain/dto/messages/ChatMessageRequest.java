package com.koniosoftworks.kvstreaming.domain.dto.messages;

/**
 * Created by nicu on 5/20/17.
 */
public class ChatMessageRequest {
    private String message;

    public ChatMessageRequest(String message) {
        this.message = message;
    }

    public ChatMessageRequest() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessageRequest{" +
                "message='" + message + '\'' +
                '}';
    }
}
