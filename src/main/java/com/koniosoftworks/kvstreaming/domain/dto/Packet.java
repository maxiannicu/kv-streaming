package com.koniosoftworks.kvstreaming.domain.dto;

import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;
import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by nicu on 5/16/17.
 */
public class Packet<T extends NetworkSerializable> implements NetworkSerializable {
    private PacketType packetType;
    private T data;

    public Packet(PacketType packetType, T data) {
        this.packetType = packetType;
        this.data = data;
    }

    public Packet() {
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "packetType=" + packetType +
                ", data=" + data +
                '}';
    }

    @Override
    public void serialize(Serializer serializer){
        serializer.put(packetType.ordinal());
        data.serialize(serializer);
    }

    @Override
    public void unserialize(Deserializer deserializer) {
        packetType = PacketType.values()[deserializer.nextInt()];
        try {
            data = (T)packetType.initializeModel();
            data.unserialize(deserializer);
        } catch (UnserializeException e) {
            e.printStackTrace();
        }
    }
}
