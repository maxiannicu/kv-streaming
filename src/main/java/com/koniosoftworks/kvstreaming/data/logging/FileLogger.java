package com.koniosoftworks.kvstreaming.data.logging;

import com.koniosoftworks.kvstreaming.domain.logging.Logger;
import com.koniosoftworks.kvstreaming.domain.logging.LogLevel;

/**
 * Created by maxim on 5/19/17.
 */
public class FileLogger extends Logger {
    public FileLogger(LogLevel logLevel) {
        super(logLevel);
    }

    @Override
    protected void write(LogLevel logLevel, String message) {
        //TODO implement file logger.
    }
}
