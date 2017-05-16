package com.koniosoftworks.kvstreaming.data.io;

import com.koniosoftworks.kvstreaming.domain.io.Serializer;
import com.koniosoftworks.kvstreaming.domain.props.MessagingProperties;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by nicu on 5/17/17.
 */
public class StreamSerializer implements Serializer {
    private OutputStream outputStream;

    public StreamSerializer(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void put(boolean val) {
        put(Boolean.toString(val));
    }

    @Override
    public void put(byte val) {
        put(Byte.toString(val));
    }

    @Override
    public void put(int val) {
        put(Integer.toString(val));
    }

    @Override
    public void put(String val) {
        try {
            outputStream.write((val+ MessagingProperties.MESSAGING_DELIMITER).getBytes(MessagingProperties.CHARSET));
        } catch (IOException e) {
            throw new RuntimeException("Cannot put into stream",e);
        }
    }

    @Override
    public void put(long val) {
        put(Long.toString(val));
    }
}
