package com.koniosoftworks.kvstreaming.domain.io;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public interface SerializationAlgorithm<T> {
    void serialize(StreamWriter streamWriter, T object);
    void deserialize(StreamReader streamReader, T object);
}
