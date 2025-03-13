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


    public static String generateUniqueSlug(String e) {
        String slug = e.replaceAll("[^a-zA-Z0-9\\s]", "")
                .replaceAll("\\s+", " ")
                .trim();
        slug = slug.toLowerCase();
        slug = slug.replaceAll("\\s", "-");
        return slug;
    }

}
