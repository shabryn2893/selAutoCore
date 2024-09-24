package io.github.shabryn2893.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for creating and managing loggers.
 */
public class LoggerUtils {

    // Private constructor to prevent instantiation
    private LoggerUtils() {
        throw new UnsupportedOperationException("LoggerUtils class should not be instantiated");
    }

    /**
     * Gets a logger for the specified class.
     *
     * @param clazz the class for which to get the logger.
     * @return a logger for the specified class.
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
