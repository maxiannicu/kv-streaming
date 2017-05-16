package com.koniosoftworks.kvstreaming.domain.dto;

import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.io.Serializer;

/**
 * Created by nicu on 5/16/17.
 */
public interface NetworkSerializable {
    void serialize(Serializer serializer);
    void unserialize(Deserializer deserializer);
}
