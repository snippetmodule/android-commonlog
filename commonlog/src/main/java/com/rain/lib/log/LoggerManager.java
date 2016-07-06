package com.rain.lib.log;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LoggerManager {
    // private static final Handler DEFAULT_HANDLER = new PatternHandler(Logger.Level.VERBOSE, "%logger",
    // "%date %caller%n");
    // private static final Logger DEFAULT_LOGGER = new SimpleLogger(Logger.ROOT_LOGGER_NAME, DEFAULT_HANDLER);
    // 在加载配置文件时，重置
    private static Handler DEFAULT_HANDLER = new NullHandler();
    private static Logger DEFAULT_LOGGER = new SimpleLogger(Logger.DEFAULT_LOGGER_NAME, DEFAULT_HANDLER);

    private static final int MAX_LOG_TAG_LENGTH = 23;

    private static final String CONF_DEFAULT = "default";
    private static final String CONF_LOGGER = "logger.";
    private static final Pattern CONF_LOGGER_REGEX = Pattern.compile("(.*?):(.*?)(:(.*)):(.*?)?");

    private static Map<String, Handler> HANDLER_MAP;
    private static final Map<String, Logger> LOGGER_CACHE = new WeakHashMap<String, Logger>();

    private LoggerManager() {
        throw new UnsupportedOperationException();
    }

    public static void init(InputStream propertyInputStream) {
        HANDLER_MAP = loadConfiguration(propertyInputStream);
    }

    private static void loadProperties(Properties properties, InputStream inputStream) throws IOException {
        try {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private static Handler decodeHandler(String loggerName, String handlerString) {
        Matcher matcher = CONF_LOGGER_REGEX.matcher(handlerString);
        if (matcher.matches()) {
            String levelString = matcher.group(1);
            String tag = matcher.group(2);
            String message = matcher.group(3);
            String file = matcher.group(5);

            if (tag == null || tag.length() == 0) {
                tag = loggerName;
            }
            if (tag.length() > 23) {
                String trimmedTag = tag.substring(0, MAX_LOG_TAG_LENGTH);
                DEFAULT_LOGGER.w("Android doesn't support tags %d characters longer. Tag '%s' will be trimmed to '%s'",
                        MAX_LOG_TAG_LENGTH, tag, trimmedTag);
                tag = trimmedTag;
            }
            try {
                return new PatternHandler(Logger.Level.valueOf(levelString), tag, message, file);
            } catch (IllegalArgumentException e) {
                DEFAULT_LOGGER.w("Cannot parse '%s' as logging level. Only %s are allowed",
                        levelString, Arrays.toString(Logger.Level.values()));
                return null;
            }
        } else {
            DEFAULT_LOGGER.w("Wrong format of logger configuration: '%s'", handlerString);
            return null;
        }
    }

    private static Map<String, Handler> loadConfiguration(InputStream propertyInputStream) {
        Map<String, Handler> handlerMap = new HashMap<String, Handler>();

        // read properties file
        Properties properties = new Properties();
        try {
            loadProperties(properties, propertyInputStream);
        } catch (IOException e) {
            DEFAULT_LOGGER.e(e, "loadConfiguration error");
            handlerMap.put(null, DEFAULT_HANDLER);
            return handlerMap;
        }

        // something is wrong if property file is empty
        if (!properties.propertyNames().hasMoreElements()) {
            DEFAULT_LOGGER.e("Logger configuration file is empty. Default configuration will be used");
            handlerMap.put(null, DEFAULT_HANDLER);
            return handlerMap;
        }

        // parse properties to logger map
        for (Enumeration<?> names = properties.propertyNames(); names.hasMoreElements(); ) {
            String propertyName = (String) names.nextElement();
            String propertyValue = properties.getProperty(propertyName);

            Handler handler = null; // = decodeHandler(loggerName, propertyValue);

            if (propertyName.equals(CONF_DEFAULT)) {
                handler = decodeHandler(Logger.DEFAULT_LOGGER_NAME, propertyValue);
                if (handler != null) {
                    DEFAULT_HANDLER = handler;
                    DEFAULT_LOGGER = new SimpleLogger(Logger.DEFAULT_LOGGER_NAME, DEFAULT_HANDLER);
                    handlerMap.put(null, handler);
                }
            } else if (propertyName.startsWith(CONF_LOGGER)) {
                String loggerName = propertyName.substring(CONF_LOGGER.length());

                if (loggerName.equalsIgnoreCase(Logger.DEFAULT_LOGGER_NAME)) {
                    loggerName = null;
                }
                handler = decodeHandler(loggerName, propertyValue);
                if (handler != null)
                    handlerMap.put(loggerName, handler);
            } else {
                DEFAULT_LOGGER.e("unknown key '%s' in '%s' file", propertyName, "properites");
            }

        }

        // logger map should have root logger (corresponding to "null" key)
        if (!handlerMap.containsKey(null)) {
            handlerMap.put(null, DEFAULT_HANDLER);
        }

        return handlerMap;
    }


    private static Handler findHandler(String name) {
        String currentKey = null;
        if (HANDLER_MAP == null) {
            DEFAULT_LOGGER.e("LoggerManager method :init not invoked");
            return DEFAULT_HANDLER;
        }
        if (name != null) {
            for (String key : HANDLER_MAP.keySet()) {
                if (key != null && name.startsWith(key)) {
                    // check that key corresponds to a name of sub-package
                    if (key.length() >= name.length()
                            || name.charAt(key.length()) == '.' || name.charAt(key.length()) == '$') {
                        // update current best matching key
                        if (currentKey == null || currentKey.length() < key.length()) {
                            currentKey = key;
                        }
                    }
                }
            }
        }
        Handler handler = HANDLER_MAP.get(currentKey);
        return handler != null ? handler : DEFAULT_HANDLER;
    }

    /**
     * Returns logger corresponding to the specified name.
     *
     * @param name the name.
     * @return the {@link Logger} implementation.
     */
    public static Logger getLogger(String name) {
        Logger logger;

        // try to find a logger in the cache
        synchronized (LOGGER_CACHE) {
            logger = LOGGER_CACHE.get(name);
        }

        // load logger from configuration
        if (logger == null) {
            logger = new SimpleLogger(name, findHandler(name));
            synchronized (LOGGER_CACHE) {
                LOGGER_CACHE.put(logger.getName(), logger);
            }
        }

        // return logger
        return logger;
    }

    /**
     * Returns logger corresponding to the specified class.
     *
     * @param aClass the class.
     * @return the {@link Logger} implementation.
     */
    public static Logger getLogger(Class<?> aClass) {
        return getLogger(aClass == null ? null : aClass.getName());
    }

    /**
     * Returns logger corresponding to the caller class.
     *
     * @return the {@link Logger} implementation.
     */
    public static Logger getLogger() {
        return getLogger(Utils.getCallerClassName());
    }

}
