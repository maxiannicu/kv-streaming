package com.koniosoftworks.kvstreaming.domain.logging;

/**
 * Created by maxim on 5/19/17.
 */
public class LoggerBuilder {

    private Logger firstLogger;
    private Logger lastLogger;

    public LoggerBuilder(Logger firstLogger) {
        this.firstLogger = firstLogger;
        this.lastLogger = firstLogger;
    }

    public LoggerBuilder appendNextLogger(Logger logger){
        lastLogger.nextLogger = logger;
        lastLogger = logger;
        return this;
    }

    public Logger build(){
        return firstLogger;
    }
}
