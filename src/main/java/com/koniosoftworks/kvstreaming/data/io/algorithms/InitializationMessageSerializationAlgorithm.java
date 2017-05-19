package com.koniosoftworks.kvstreaming.data.io.algorithms;

import com.koniosoftworks.kvstreaming.domain.dto.messages.ChatMessage;
import com.koniosoftworks.kvstreaming.domain.dto.messages.InitializationMessage;
import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.SerializationAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class InitializationMessageSerializationAlgorithm implements SerializationAlgorithm<InitializationMessage> {
    @Override
    public void serialize(Serializer serializer, InitializationMessage object) {
        serializer.put(object.getUdpPort());
        serializer.put(object.getUsername());
    }

    @Override
    public void deserialize(Deserializer deserializer, InitializationMessage object) {
        object.setUdpPort(deserializer.nextInt());
        object.setUsername(deserializer.nextString());
    }
}
