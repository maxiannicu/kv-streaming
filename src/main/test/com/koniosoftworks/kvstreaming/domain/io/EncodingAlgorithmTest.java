package com.koniosoftworks.kvstreaming.domain.io;

import com.koniosoftworks.kvstreaming.data.io.Base64Encoding;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Nicu Maxian on 5/19/2017.
 */
public class EncodingAlgorithmTest {
    private EncodingAlgorithm encodingAlgorithm;

    @Before
    public void setUp() throws Exception {
        encodingAlgorithm = new Base64Encoding();
    }

    @Test
    public void testStringEncodeDecode() throws Exception {
        String string = "hello world";
        byte[] encodeBytes = encodingAlgorithm.encode(string.getBytes());
        byte[] decode = encodingAlgorithm.decode(encodeBytes);

        assertEquals(string,new String(decode));
    }

    @Test
    public void testByteArrayEncodeDecode() throws Exception {
        byte[] data = new byte[]{1,2,3,4,5,6,7,8,9};
        byte[] encodeBytes = encodingAlgorithm.encode(data);
        byte[] decode = encodingAlgorithm.decode(encodeBytes);

        assertArrayEquals(data,decode);
    }

}