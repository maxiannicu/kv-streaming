package com.koniosoftworks.kvstreaming.domain.dto.messages;

import java.util.Date;

/**
 * Created by nicu on 5/15/17.
 */
public class ChatMessage {
    private String sender;
    private String message;
    private Date sentOnUtc;

    public ChatMessage(String sender, String message, Date sentOnUtc) {
        this.sender = sender;
        this.message = message;
        this.sentOnUtc = sentOnUtc;
    }

    public ChatMessage() {
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Date getSentOnUtc() {
        return sentOnUtc;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSentOnUtc(Date sentOnUtc) {
        this.sentOnUtc = sentOnUtc;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", sentOnUtc=" + sentOnUtc +
                '}';
    }
}
