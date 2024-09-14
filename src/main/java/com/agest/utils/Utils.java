package com.agest.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
    public static String buildErrorMessage(String actual, String expected, String prefix) {
        return String.format("%s - Expected value is '%s' but was '%s'", prefix, expected, actual);
    }

    public static String buildErrorMessage(Object actual, Object expected, String prefix) {
        return String.format("%s - Expected value is '%s' but was '%s'", prefix, expected, actual);
    }

    public static String buildErrorMessage(String actual, String expected) {
        return String.format("Expected value is '%s' but was '%s'", expected, actual);
    }
}
