package com.koniosoftworks.kvstreaming.domain.logging;

/**
 * Created by maxim on 5/19/17.
 */
public abstract class Logger {
    protected Logger nextLogger;

    protected LogLevel logLevel;

    public Logger(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    void setNextLogger(Logger nextLogger){
        this.nextLogger = nextLogger;
    }

    public void log(LogLevel logLevel, String message){
        if(this.logLevel.accept(logLevel)){
            write(logLevel, message);
        }
        if(nextLogger != null){
            nextLogger.log(logLevel, message);
        }
    }

    abstract protected void write(LogLevel logLevel, String message);

    public void quite(String message){
        log(LogLevel.QUITE, message);
    }

    public void error(String message){
        log(LogLevel.ERROR, message);
    }

    public void warning(String message){
        log(LogLevel.WARNING, message);
    }

    public void info(String message){
        log(LogLevel.INFO, message);
    }

    public void debug(String message){
        log(LogLevel.DEBUG, message);
    }

    public void verbose(String message){
        log(LogLevel.VERBOSE, message);
    }
}
