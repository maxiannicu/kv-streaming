package com.koniosoftworks.kvstreaming.data.io;

import com.koniosoftworks.kvstreaming.domain.io.Deserializer;
import com.koniosoftworks.kvstreaming.domain.props.MessagingProperties;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by nicu on 5/17/17.
 */
public class ScannerDeserealizer implements Deserializer {
    private Scanner scanner;

    public ScannerDeserealizer(InputStream inputStream) {
        this.scanner = new Scanner(inputStream,MessagingProperties.CHARSET);
        this.scanner.useDelimiter(MessagingProperties.MESSAGING_DELIMITER);
    }

    @Override
    public boolean hasNextBoolean() {
        return scanner.hasNextBoolean();
    }

    @Override
    public boolean hasNextByte() {
        return scanner.hasNextByte();
    }

    @Override
    public boolean hasNextInt() {
        return scanner.hasNextInt();
    }

    @Override
    public boolean hasNextString() {
        return scanner.hasNext();
    }

    @Override
    public boolean hasNextLong() {
        return scanner.hasNextLong();
    }

    @Override
    public boolean nextBoolean() {
        return scanner.nextBoolean();
    }

    @Override
    public byte nextByte() {
        return scanner.nextByte();
    }

    @Override
    public int nextInt() {
        return scanner.nextInt();
    }

    @Override
    public String nextString() {
        return scanner.next();
    }


    @Override
    public long nextLong() {
        return scanner.nextLong();
    }
}
