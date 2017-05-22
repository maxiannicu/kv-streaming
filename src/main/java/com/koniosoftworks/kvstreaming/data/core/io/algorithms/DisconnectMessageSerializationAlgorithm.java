package com.koniosoftworks.kvstreaming.data.core.io.algorithms;

import com.koniosoftworks.kvstreaming.domain.dto.messages.DisconnectMessage;
import com.koniosoftworks.kvstreaming.domain.io.SerializationAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.StreamReader;
import com.koniosoftworks.kvstreaming.domain.io.StreamWriter;

/**
 * Created by max on 5/20/17.
 */
public class DisconnectMessageSerializationAlgorithm implements SerializationAlgorithm<DisconnectMessage> {
    @Override
    public void serialize(StreamWriter streamWriter, DisconnectMessage object) {
        streamWriter.put(object.getMessage());
    }

    @Override
    public void deserialize(StreamReader streamReader, DisconnectMessage object) {
        object.setMessage(streamReader.nextString());
    }
}
