package com.koniosoftworks.kvstreaming.domain.exception;

import java.io.IOException;

/**
 * Created by nicu on 5/16/17.
 */
public class SerializeException extends IOException {
    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
