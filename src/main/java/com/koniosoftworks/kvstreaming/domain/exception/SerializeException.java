package com.koniosoftworks.kvstreaming.domain.exception;

/**
 * Created by nicu on 5/16/17.
 */
public class SerializeException extends Exception {
    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
