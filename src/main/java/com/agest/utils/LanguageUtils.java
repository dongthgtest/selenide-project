package com.agest.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageUtils {
    private final ResourceBundle resourceBundle;

    public LanguageUtils(String languageCode) {
        Locale locale = new Locale(languageCode);
        resourceBundle = ResourceBundle.getBundle(String.valueOf(locale));
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }
}
