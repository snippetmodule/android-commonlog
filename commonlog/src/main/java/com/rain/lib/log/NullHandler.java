package com.rain.lib.log;

public class NullHandler implements Handler {

    public NullHandler() {

    }

    @Override
    public boolean isEnabled(Logger.Level level) {
        return false;
    }

    @Override
    public void print(String loggerName, Logger.Level level,
            Throwable throwable, String messageFormat, Object... args) throws IllegalArgumentException {
    }

}
