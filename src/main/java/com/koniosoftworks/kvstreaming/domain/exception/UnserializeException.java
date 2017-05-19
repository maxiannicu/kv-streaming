package com.koniosoftworks.kvstreaming.domain.exception;

import java.io.IOException;

/**
 * Created by nicu on 5/16/17.
 */
public class UnserializeException extends IOException {

    public UnserializeException(String message) {
        super(message);
    }

    public UnserializeException(String message, Throwable cause) {
        super(message, cause);
    }


}
