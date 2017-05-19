package com.koniosoftworks.kvstreaming.domain.io;

/**
 * Created by nicu on 5/17/17.
 */
public interface StreamWriter {
    void put(boolean val);
    void put(byte val);
    void put(int val);
    void put(String val);
    void put(long val);
}
