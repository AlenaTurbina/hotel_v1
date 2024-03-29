package com.hotel_server.message;

import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Class for message receiving configuration from properties-file source
 *
 * @author Alena Turbina
 */

public class Messages {

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("server_configuration");

    /**
     * Method for getting message by the key from properties
     *
     * @param key key of message in the properties
     * @return String message
     */

    public static String getMessage(String key) {
        return MESSAGES.getString(key);
    }

    /**
     * Method for getting String message by the key from properties and convert into Integer
     *
     * @param key key of message in the properties
     * @return Integer message
     */

    public static Integer getIntegerMessage(String key) {
        return Integer.parseInt(MESSAGES.getString(key));
    }


    public static UUID getUUIDMessage(String key) {
        return UUID.fromString(MESSAGES.getString(key));
    }
}
