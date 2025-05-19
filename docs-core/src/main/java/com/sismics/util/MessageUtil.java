package com.sismics.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageUtil {
    public static String getMessage(Locale locale, String key, Object... args) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale cannot be null");
        }
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        ResourceBundle resources = ResourceBundle.getBundle("messages", locale);
        String message;
        try {
            message = resources.getString(key);
        } catch (MissingResourceException e) {
            message = "**" + key + "**";
        }
        return MessageFormat.format(message, args);
    }

    public static ResourceBundle getMessage(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale cannot be null");
        }
        return ResourceBundle.getBundle("messages", locale);
    }
}