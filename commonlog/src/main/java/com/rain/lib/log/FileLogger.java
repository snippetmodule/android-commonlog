package com.rain.lib.log;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger extends AbstractLogger {
    private String path = "";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private String savedLogPath;

    public FileLogger(String path, String name) {
        super(name + "_" + System.currentTimeMillis());
        this.path = path;
        savedLogPath = Environment.getExternalStorageDirectory().getPath() + "/log" + this.path +
                getName() + ".log";
        File saveFile = new File(savedLogPath);
        (saveFile.getParentFile()).mkdirs();
    }

    @Override
    public boolean isEnabled(Level level) {
        return true;
    }

    @Override
    public void print(Level level, String message, Throwable throwable) {
        print(getName(), level, throwable, message);

    }

    @Override
    public void print(Level level, Throwable throwable, String messageFormat, Object... args) {
        messageFormat = messageFormat.replaceAll("\\{\\}", "%s");
        print(getName(), level, throwable, messageFormat, args);
    }

    private void print(String loggerName, Logger.Level level,
                       Throwable throwable, String messageFormat, Object... args) throws IllegalArgumentException {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (isEnabled(level)) {
            String message;

            if (messageFormat == null) {
                if (args != null && args.length > 0) {
                    throw new IllegalArgumentException("message format is not set but arguments are presented");
                }

                if (throwable == null) {
                    message = "";
                } else {
                    message = android.util.Log.getStackTraceString(throwable);
                }
            } else {
                if (throwable == null) {
                    if (args.length != 0)
                        message = String.format(messageFormat, args);
                    else
                        message = messageFormat;
                } else {
                    if (args.length != 0)
                        message = String.format(messageFormat, args) + '\n' + android.util.Log.getStackTraceString(throwable);
                    else
                        message = messageFormat + '\n' + android.util.Log.getStackTraceString(throwable);
                }
            }
//            android.util.Log.println(level.intValue(), getName(),  message);
            printToFile(level.name(), message);
        }
    }

    private void printToFile(String tag, String message) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                FileOutputStream fos = new FileOutputStream(savedLogPath, true);
                message = dateFormat.format(new Date()) + "\t" + message + "\n";
                fos.write(message.toString().getBytes());
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
