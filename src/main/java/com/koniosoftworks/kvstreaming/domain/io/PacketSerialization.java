package com.koniosoftworks.kvstreaming.domain.io;

import com.koniosoftworks.kvstreaming.domain.dto.Packet;
import com.koniosoftworks.kvstreaming.domain.exception.SerializeException;

import java.io.IOException;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public interface PacketSerialization {
    byte[] serialize(Packet packet) throws IOException;
    Packet unserialize(byte[] bytes) throws IOException;
}
