package com.rain.lib.log;

/**
 * The log message handler.
 */
public interface Handler {

    /**
     * Checks if the specified log level is enabled or not for this handler.
     *
     * @param level the level.
     * @return Are messages with this level allowed to be logged or not.
     */
    public boolean isEnabled(Logger.Level level);

    /**
     * Prints a log message.
     * <p/>
     * This method should automatically check using {@link #isEnabled(Logger.Level)} method
     * if the message is allowed to be logged or not.
     * <p/>
     * The format string of the log message should be formatted
     * according to rules of the standard format string described
     * in JavaDoc of {@link java.util.Formatter}.
     * Implementations can use {@link java.util.Formatter#format(String, Object...)}
     * to prepare the log message from format string and array of arguments.
     *
     * @param loggerName    a name of a logger that user used to log message.
     * @param level         a level of the log message
     * @param throwable     a throwable object or {@code null}.
     * @param messageFormat a format string of the log message. Can be {@code null}.
     * @param args          an array of arguments. Can be {@code null}
     *                      which is considered as an empty array.
     * @throws IllegalArgumentException if no format string is specified but arguments are presented.
     */
    public void print(String loggerName, Logger.Level level,
                      Throwable throwable, String messageFormat, Object... args) throws IllegalArgumentException;

}
