package com.agest.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageUtils {
    private final ResourceBundle resourceBundle;

    public LanguageUtils(String pageName, String languageCode) {
        Locale locale = new Locale(languageCode);
        resourceBundle = ResourceBundle.getBundle(pageName, locale);
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }
}
