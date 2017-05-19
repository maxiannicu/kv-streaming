package com.koniosoftworks.kvstreaming.data.logging;

import com.koniosoftworks.kvstreaming.domain.logging.LogLevel;
import com.koniosoftworks.kvstreaming.domain.logging.Logger;

import java.util.logging.Level;

/**
 * Created by maxim on 5/19/17.
 */
public class ConsoleLogger extends Logger {

    private final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(ConsoleLogger.class.getName());

    public ConsoleLogger(LogLevel logLevel) {
        super(logLevel);
    }

    @Override
    protected void write(LogLevel logLevel, String message) {
        LOGGER.log(getLevel(logLevel), message);
    }

    private Level getLevel(LogLevel logLevel) {
        switch (logLevel) {
            case QUITE:
                return Level.ALL;
            case ERROR:
                return Level.SEVERE;
            case WARNING:
                return Level.WARNING;
            case INFO:
                return Level.INFO;
            case DEBUG:
                return Level.CONFIG;
            case VERBOSE:
                return Level.FINEST;
            default:
                return Level.OFF;
        }
    }
}
