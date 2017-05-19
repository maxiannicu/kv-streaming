package com.koniosoftworks.kvstreaming.domain.logging;

/**
 * Created by maxim on 5/19/17.
 */
public enum LogLevel {
    QUITE(1 , "[QUITE]"),
    ERROR(2, "[ERROR]"),
    WARNING(3, "[WARNING]"),
    INFO(4, "[INFO]"),
    DEBUG(5, "[DEBUG]"),
    VERBOSE(6, "[VERBOSE]");

    private int priority;
    private String levelName;

    LogLevel(int priority, String levelName) {
        this.priority = priority;
        this.levelName = levelName;
    }

    public boolean accept(LogLevel logLevel){
        return logLevel.priority >= this.priority;
    }
    public String getName(){
        return levelName;
    }
}