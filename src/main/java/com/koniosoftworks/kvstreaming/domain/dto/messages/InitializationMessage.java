package com.koniosoftworks.kvstreaming.domain.dto.messages;

/**
 * Created by nicu on 5/16/17.
 */
public class InitializationMessage {
    private String username;

    public InitializationMessage(String username) {
        this.username = username;
    }

    public InitializationMessage() {
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "InitializationMessage{" +
                "username='" + username + '\'' +
                '}';
    }
}
