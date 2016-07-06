package com.rain.lib.log;

/**
 * Simple implementation of {@link com.rain.lib.log.Logger} that prints all messages using {@link Handler} interface.
 */
public class SimpleLogger extends AbstractLogger {

    private final Handler handler;

    /**
     * Creates new {@link com.rain.lib.log.SimpleLogger} instance.
     * 
     * @param name
     *            the name of the logger.
     * @param handler
     *            the handler to log messages.
     */
    public SimpleLogger(String name, Handler handler) {
        super(name);
        this.handler = handler;
    }

    @Override
    public boolean isEnabled(Level level) {
        return handler != null && handler.isEnabled(level);
    }

    @Override
    public void print(Level level, String message, Throwable throwable) {
        if (handler != null) {
            handler.print(getName(), level, throwable, message);
        }
    }

    @Override
    public void print(Level level, Throwable throwable, String messageFormat, Object... args) {
        if (handler != null) {
            messageFormat = messageFormat.replaceAll("\\{\\}", "%s");
            handler.print(getName(), level, throwable, messageFormat, args);
        }
    }
}
