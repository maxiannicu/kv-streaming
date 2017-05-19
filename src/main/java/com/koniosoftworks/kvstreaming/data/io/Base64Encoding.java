package com.koniosoftworks.kvstreaming.data.io;

import com.koniosoftworks.kvstreaming.domain.io.EncodingAlgorithm;

import java.util.Base64;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class Base64Encoding implements EncodingAlgorithm {
    @Override
    public byte[] encode(byte[] decryptedBytes) {
        return Base64.getEncoder().encode(decryptedBytes);
    }

    @Override
    public byte[] decode(byte[] encryptedBytes) {
        return Base64.getDecoder().decode(encryptedBytes);
    }
}
