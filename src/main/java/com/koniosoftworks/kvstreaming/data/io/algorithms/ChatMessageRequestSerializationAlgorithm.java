package com.koniosoftworks.kvstreaming.data.io.algorithms;

import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessageRequest;
import com.koniosoftworks.kvstreaming.domain.io.SerializationAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.StreamReader;
import com.koniosoftworks.kvstreaming.domain.io.StreamWriter;

/**
 * Created by nicu on 5/20/17.
 */
public class ChatMessageRequestSerializationAlgorithm implements SerializationAlgorithm<ChatMessageRequest> {
    @Override
    public void serialize(StreamWriter streamWriter, ChatMessageRequest object) {
        streamWriter.put(object.getMessage());
    }

    @Override
    public void deserialize(StreamReader streamReader, ChatMessageRequest object) {
        object.setMessage(streamReader.nextString());
    }
}
