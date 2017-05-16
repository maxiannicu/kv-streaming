package com.koniosoftworks.kvstreaming.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nicu on 5/15/17.
 */
public class Message implements Serializable {
    private final String sender;
    private final String message;
    private final Date sentOnUtc;

    public Message(String sender, String message, Date sentOnUtc) {
        this.sender = sender;
        this.message = message;
        this.sentOnUtc = sentOnUtc;
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

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", sentOnUtc=" + sentOnUtc +
                '}';
    }
}
