package org.exercise.taskmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            LOGGER.info("Hello world");
        } catch (Throwable t) {
            LOGGER.error("Error executing Application. {}", t.getMessage());
            LOGGER.debug("Exception: {}", t.getMessage(), t);
        }
    }
}
