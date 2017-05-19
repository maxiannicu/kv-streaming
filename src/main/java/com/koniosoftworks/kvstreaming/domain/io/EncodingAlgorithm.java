package com.koniosoftworks.kvstreaming.domain.io;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public interface EncodingAlgorithm {
    byte[] encode(byte[] decryptedBytes);
    byte[] decode(byte[] encryptedBytes);
}
