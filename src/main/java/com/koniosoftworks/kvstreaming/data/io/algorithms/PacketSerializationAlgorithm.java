package com.koniosoftworks.kvstreaming.data.io.algorithms;

import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;
import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.SerializationAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class PacketSerializationAlgorithm implements SerializationAlgorithm<Packet> {
    @Override
    public void serialize(Serializer serializer, Packet object) {
        serializer.put(object.getPacketType().ordinal());
    }

    @Override
    public void deserialize(Deserializer deserializer, Packet object) {
        object.setPacketType(PacketType.values()[deserializer.nextInt()]);
    }

}
