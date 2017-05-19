package com.koniosoftworks.kvstreaming.data.io.algorithms;

import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.io.StreamReader;
import com.koniosoftworks.kvstreaming.domain.io.SerializationAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.StreamWriter;

import java.util.Date;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class ChatMessageSerializationAlgorithm implements SerializationAlgorithm<ChatMessage> {
    @Override
    public void serialize(StreamWriter streamWriter, ChatMessage object) {
        streamWriter.put(object.getSender());
        streamWriter.put(object.getMessage());
        streamWriter.put(object.getSentOnUtc().getTime());
    }

    @Override
    public void deserialize(StreamReader streamReader, ChatMessage object) {
        object.setSender(streamReader.nextString());
        object.setMessage(streamReader.nextString());
        object.setSentOnUtc(new Date(streamReader.nextLong()));
    }
}
