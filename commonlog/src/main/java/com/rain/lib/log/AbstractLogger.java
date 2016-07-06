package com.rain.lib.log;

/**
 * Abstract implementation of {@link Logger} interface.
 */
public abstract class AbstractLogger extends Logger {

    private final String name;

    /**
     * Constructor of {@link com.rain.lib.log.AbstractLogger}.
     *
     * @param name
     *            the name of the logger.
     */
    public AbstractLogger(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void v(String message, Throwable throwable) {
        print(Level.VERBOSE, message, throwable);
    }

    @Override
    public void d(String message, Throwable throwable) {
        print(Level.DEBUG, message, throwable);
    }

    @Override
    public void i(String message, Throwable throwable) {
        print(Level.INFO, message, throwable);
    }

    @Override
    public void w(String message, Throwable throwable) {
        print(Level.WARN, message, throwable);
    }

    @Override
    public void e(String message, Throwable throwable) {
        print(Level.ERROR, message, throwable);
    }

    @Override
    public void a(String message, Throwable throwable) {
        print(Level.ASSERT, message, throwable);
    }

    @Override
    public void v(Throwable throwable) {
        print(Level.VERBOSE, null, throwable);
    }

    @Override
    public void d(Throwable throwable) {
        print(Level.DEBUG, null, throwable);
    }

    @Override
    public void i(Throwable throwable) {
        print(Level.INFO, null, throwable);
    }

    @Override
    public void w(Throwable throwable) {
        print(Level.WARN, null, throwable);
    }

    @Override
    public void e(Throwable throwable) {
        print(Level.ERROR, null, throwable);
    }

    @Override
    public void a(Throwable throwable) {
        print(Level.ASSERT, null, throwable);
    }

    @Override
    public void v(Throwable throwable, String messageFormat, Object... args) {
        print(Level.VERBOSE, throwable, messageFormat, args);
    }

    @Override
    public void d(Throwable throwable, String messageFormat, Object... args) {
        print(Level.DEBUG, throwable, messageFormat, args);
    }

    @Override
    public void i(Throwable throwable, String messageFormat, Object... args) {
        print(Level.INFO, throwable, messageFormat, args);
    }

    @Override
    public void w(Throwable throwable, String messageFormat, Object... args) {
        print(Level.WARN, throwable, messageFormat, args);
    }

    @Override
    public void e(Throwable throwable, String messageFormat, Object... args) {
        print(Level.ERROR, throwable, messageFormat, args);
    }

    @Override
    public void a(Throwable throwable, String messageFormat, Object... args) {
        print(Level.ASSERT, throwable, messageFormat, args);
    }

    @Override
    public void v(String messageFormat, Object... args) {
        print(Level.VERBOSE, null, messageFormat, args);
    }

    @Override
    public void d(String messageFormat, Object... args) {
        print(Level.DEBUG, null, messageFormat, args);
    }

    @Override
    public void i(String messageFormat, Object... args) {
        print(Level.INFO, null, messageFormat, args);
    }

    @Override
    public void w(String messageFormat, Object... args) {
        print(Level.WARN, null, messageFormat, args);
    }

    @Override
    public void e(String messageFormat, Object... args) {
        print(Level.ERROR, null, messageFormat, args);
    }

    @Override
    public void a(String messageFormat, Object... args) {
        print(Level.ASSERT, null, messageFormat, args);
    }

    @Override
    public void verbose(String message) {
        print(Level.VERBOSE, message, null);
    }

    @Override
    public void warn(String message) {
        print(Level.WARN, message, null);
    }

    @Override
    public void debug(String message) {
        print(Level.DEBUG, message, null);
    }

    @Override
    public void info(String message) {
        print(Level.INFO, message, null);
    }

    @Override
    public void error(String message) {
        print(Level.ERROR, message, null);
    }

    @Override
    public void verbose(String messageFormat, Object arg1) {
        print(Level.VERBOSE, null, messageFormat, arg1);
    }

    @Override
    public void warn(String messageFormat, Object arg1) {
        print(Level.WARN, null, messageFormat, arg1);
    }

    @Override
    public void debug(String messageFormat, Object arg1) {
        print(Level.DEBUG, null, messageFormat, arg1);
    }

    @Override
    public void info(String messageFormat, Object arg1) {
        print(Level.INFO, null, messageFormat, arg1);
    }

    @Override
    public void error(String messageFormat, Object arg1) {
        print(Level.ERROR, null, messageFormat, arg1);
    }

    @Override
    public void verbose(String messageFormat, Object arg1, Object arg2) {
        print(Level.VERBOSE, null, messageFormat, arg1, arg2);
    }

    @Override
    public void warn(String messageFormat, Object arg1, Object arg2) {
        print(Level.WARN, null, messageFormat, arg1, arg2);
    }

    @Override
    public void debug(String messageFormat, Object arg1, Object arg2) {
        print(Level.DEBUG, null, messageFormat, arg1, arg2);
    }

    @Override
    public void info(String messageFormat, Object arg1, Object arg2) {
        print(Level.INFO, null, messageFormat, arg1, arg2);
    }

    @Override
    public void error(String message, Object arg1, Object arg2) {
        print(Level.ERROR, null, message, arg1, arg2);
    }

    @Override
    public void verbose(Throwable throwable, String messageFormat, Object... args) {
        print(Level.VERBOSE, throwable, messageFormat, args);
    }

    @Override
    public void warn(Throwable throwable, String messageFormat, Object... args) {
        print(Level.WARN, throwable, messageFormat, args);
    }

    @Override
    public void debug(Throwable throwable, String messageFormat, Object... args) {
        print(Level.DEBUG, throwable, messageFormat, args);
    }

    @Override
    public void info(Throwable throwable, String messageFormat, Object... args) {
        print(Level.INFO, throwable, messageFormat, args);
    }

    @Override
    public void error(Throwable throwable, String messageFormat, Object... args) {
        print(Level.ERROR, throwable, messageFormat, args);
    }

    @Override
    public void verbose(String message, Throwable throwable) {
        print(Level.VERBOSE, message, throwable);
    }

    @Override
    public void warn(String message, Throwable throwable) {
        print(Level.WARN, message, throwable);
    }

    @Override
    public void debug(String message, Throwable throwable) {
        print(Level.DEBUG, message, throwable);
    }

    @Override
    public void info(String message, Throwable throwable) {
        print(Level.INFO, message, throwable);
    }

    @Override
    public void error(String message, Throwable throwable) {
        print(Level.ERROR, message, throwable);
    }

    @Override
    public void verbose(Throwable throwable) {
        print(Level.VERBOSE, null, throwable);
    }

    @Override
    public void warn(Throwable throwable) {
        print(Level.WARN, null, throwable);
    }

    @Override
    public void debug(Throwable throwable) {
        print(Level.DEBUG, null, throwable);
    }

    @Override
    public void info(Throwable throwable) {
        print(Level.INFO, null, throwable);
    }

    @Override
    public void error(Throwable throwable) {
        print(Level.ERROR, null, throwable);

    }

    @Override
    public void verbose(String messageFormat, Object... args) {
        print(Level.VERBOSE, null, messageFormat, args);

    }

    @Override
    public void debug(String messageFormat, Object... args) {
        print(Level.DEBUG, null, messageFormat, args);

    }

    @Override
    public void info(String messageFormat, Object... args) {
        print(Level.INFO, null, messageFormat, args);

    }

    @Override
    public void warn(String messageFormat, Object... args) {
        print(Level.WARN, null, messageFormat, args);

    }

    @Override
    public void error(String messageFormat, Object... args) {
        print(Level.ERROR, null, messageFormat, args);

    }
}
