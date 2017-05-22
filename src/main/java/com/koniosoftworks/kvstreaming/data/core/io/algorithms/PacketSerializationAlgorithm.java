package com.koniosoftworks.kvstreaming.data.core.io.algorithms;

import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.dto.PacketType;
import com.koniosoftworks.kvstreaming.domain.io.StreamReader;
import com.koniosoftworks.kvstreaming.domain.io.SerializationAlgorithm;
import com.koniosoftworks.kvstreaming.domain.io.StreamWriter;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class PacketSerializationAlgorithm implements SerializationAlgorithm<Packet> {
    @Override
    public void serialize(StreamWriter streamWriter, Packet object) {
        streamWriter.put(object.getPacketType().ordinal());
    }

    @Override
    public void deserialize(StreamReader streamReader, Packet object) {
        object.setPacketType(PacketType.values()[streamReader.nextInt()]);
    }

}
