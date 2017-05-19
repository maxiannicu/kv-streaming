package com.koniosoftworks.kvstreaming.data.io.algorithms;

import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.SerializationAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;

import java.util.Date;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class ChatMessageSerializationAlgorithm implements SerializationAlgorithm<ChatMessage> {
    @Override
    public void serialize(Serializer serializer, ChatMessage object) {
        serializer.put(object.getSender());
        serializer.put(object.getMessage());
        serializer.put(object.getSentOnUtc().getTime());
    }

    @Override
    public void deserialize(Deserializer deserializer, ChatMessage object) {
        object.setSender(deserializer.nextString());
        object.setMessage(deserializer.nextString());
        object.setSentOnUtc(new Date(deserializer.nextLong()));
    }
}
