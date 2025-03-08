package com.warehouse.app.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class UtilsHelper {

    private static MessageSource messageSource;

    @Autowired
    public UtilsHelper(MessageSource messageSource) {
        UtilsHelper.messageSource = messageSource;
    }

    public static String generateAvatar(String name) {
        return "https://ui-avatars.com/api/?name=" + name;
    }

    public static String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.ENGLISH);
    }

}
