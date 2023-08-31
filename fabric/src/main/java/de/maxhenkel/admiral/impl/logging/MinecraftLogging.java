package de.maxhenkel.admiral.impl.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MinecraftLogging {

    public static final Logger LOGGER = LogManager.getLogger(Log.LOGGER.getName());

    public static void useMinecraftLogger() {
        Log.LOGGER.setUseParentHandlers(false);
        Log.LOGGER.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                LOGGER.log(fromJavaLogLevel(record.getLevel()), record.getMessage(), record.getThrown());
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
        });
    }

    private static Level fromJavaLogLevel(java.util.logging.Level javaLevel) {
        if (javaLevel.equals(java.util.logging.Level.SEVERE)) {
            return Level.ERROR;
        } else if (javaLevel.equals(java.util.logging.Level.WARNING)) {
            return Level.WARN;
        } else if (javaLevel.equals(java.util.logging.Level.INFO)) {
            return Level.INFO;
        } else if (javaLevel.equals(java.util.logging.Level.CONFIG)) {
            return Level.DEBUG;
        } else if (javaLevel.equals(java.util.logging.Level.FINE)) {
            return Level.DEBUG;
        } else if (javaLevel.equals(java.util.logging.Level.FINER)) {
            return Level.DEBUG;
        } else if (javaLevel.equals(java.util.logging.Level.FINEST)) {
            return Level.TRACE;
        } else {
            return Level.INFO;
        }
    }

}
