package com.koniosoftworks.kvstreaming.domain.exception;

/**
 * Created by nicu on 5/16/17.
 */
public class UnserializeException extends Exception {

    public UnserializeException(String message) {
        super(message);
    }

    public UnserializeException(String message, Throwable cause) {
        super(message, cause);
    }


}
