package com.koniosoftworks.kvstreaming.domain.io;

/**
 * Created by nicu on 5/17/17.
 */
public interface StreamReader {
    boolean hasNextBoolean();
    boolean hasNextByte();
    boolean hasNextInt();
    boolean hasNextString();
    boolean hasNextLong();

    boolean nextBoolean();
    byte nextByte();
    int nextInt();
    String nextString();
    long nextLong();
}
