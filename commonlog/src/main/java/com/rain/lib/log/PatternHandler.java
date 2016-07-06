package com.rain.lib.log;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatternHandler implements Handler {

    private final Logger.Level level;
    private final String tagPattern;
    private final String messagePattern;
    private final Pattern compiledTagPattern;
    private final Pattern compiledMessagePattern;
    private final String filePattern;
    private final String time;

    /**
     * Creates new {@link com.rain.lib.log.PatternHandler}.
     *
     * @param level          the level.
     * @param tagPattern     the tag pattern.
     * @param messagePattern the message pattern.
     */
    public PatternHandler(Logger.Level level, String tagPattern, String messagePattern, String filePattern) {
        this.level = level;
        this.tagPattern = tagPattern;
        this.compiledTagPattern = Pattern.compile(tagPattern);
        this.messagePattern = messagePattern;
        this.compiledMessagePattern = Pattern.compile(messagePattern);
        this.filePattern = filePattern;
        this.time = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
    }

    /**
     * Returns the level.
     *
     * @return the level.
     */
    public Logger.Level getLevel() {
        return level;
    }

    /**
     * Returns the tag messagePattern.
     *
     * @return the tag messagePattern.
     */
    public String getTagPattern() {
        return tagPattern;
    }

    /**
     * Returns the message messagePattern.
     *
     * @return the message messagePattern.
     */
    public String getMessagePattern() {
        return messagePattern;
    }

    @Override
    public boolean isEnabled(Logger.Level level) {
        return this.level != null && level != null && this.level.includes(level);
    }

    @Override
    public void print(String loggerName, Logger.Level level,
                      Throwable throwable, String messageFormat, Object... args) throws IllegalArgumentException {
        if (isEnabled(level)) {
            String message;

            if (messageFormat == null) {
                if (args != null && args.length > 0) {
                    throw new IllegalArgumentException("message format is not set but arguments are presented");
                }

                if (throwable == null) {
                    message = "";
                } else {
                    message = Log.getStackTraceString(throwable);
                }
            } else {
                if (throwable == null) {
                    if (args.length != 0)
                        message = String.format(messageFormat, args);
                    else
                        message = messageFormat;
                } else {
                    if (args.length != 0)
                        message = String.format(messageFormat, args) + '\n' + Log.getStackTraceString(throwable);
                    else
                        message = messageFormat + '\n' + Log.getStackTraceString(throwable);
                }
            }

            StackTraceElement caller = null;
            if ((compiledTagPattern != null && compiledTagPattern.isCallerNeeded())
                    || (compiledMessagePattern != null && compiledMessagePattern.isCallerNeeded())) {
                caller = Utils.getCaller();
            }

            String tag = compiledTagPattern == null ? loggerName : compiledTagPattern.apply(caller, loggerName, level);
            String messageHead = compiledMessagePattern == null ? "" : compiledMessagePattern.apply(caller, loggerName,
                    level);

            if (messageHead.length() > 0 && !Character.isWhitespace(messageHead.charAt(0))) {
                messageHead = messageHead + " ";
            }
            if (filePattern == null || filePattern.length() == 0)
                Log.println(level.intValue(), tag, messageHead + message);
            else {
                printToFile(tag, message);
            }
        }
    }

    private void printToFile(String tag, String message) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getPath() + filePattern +
                        tag + time + ".log";
                File saveFile = new File(path);
                (saveFile.getParentFile()).mkdirs();

                FileOutputStream fos = new FileOutputStream(path, true);
                message = message + "\n";
                fos.write(message.toString().getBytes());
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
