package com.koniosoftworks.kvstreaming.domain.dto;

import com.koniosoftworks.kvstreaming.domain.exception.UnserializeException;
import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;

/**
 * Created by nicu on 5/16/17.
 */
public class Packet<T> {
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

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "packetType=" + packetType +
                ", data=" + data +
                '}';
    }


}
