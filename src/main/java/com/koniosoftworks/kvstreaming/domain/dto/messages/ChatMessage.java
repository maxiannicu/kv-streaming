package com.koniosoftworks.kvstreaming.domain.dto.messages;

import com.koniosoftworks.kvstreaming.domain.dto.NetworkSerializable;
import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by nicu on 5/15/17.
 */
public class ChatMessage implements NetworkSerializable {
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

    @Override
    public String toString() {
        return "ChatMessage{" +
                "sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", sentOnUtc=" + sentOnUtc +
                '}';
    }

    @Override
    public void serialize(Serializer serializer) {
        serializer.put(sender);
        serializer.put(message);
        serializer.put(sentOnUtc.getTime());
    }

    @Override
    public void unserialize(Deserializer deserializer) {
        sender = deserializer.nextString();
        message = deserializer.nextString();
        sentOnUtc = new Date(deserializer.nextLong());
    }
}
