package com.agest.utils;

public class Utils {
    public static String getErrorMessage(String actual, String expected) {
        return String.format("Expected alert message: '%s', but found: '%s'", expected, actual);
    }
}
