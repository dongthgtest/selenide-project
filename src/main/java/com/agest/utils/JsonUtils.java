package com.agest.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private static JsonReader getJsonReader(String jsonPath) {
        JsonReader reader;
        try {
            reader = new JsonReader(new InputStreamReader(new FileInputStream(jsonPath), "UTF-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            throw new RuntimeException("Cannot read json file from " + jsonPath + "\n" + e.getMessage());
        }
        return reader;
    }

    public static <T> List<T> toList(String jsonPath, Class<T> clazz) {
        Type type = TypeToken.getParameterized(Collection.class, clazz).getType();
        log.debug("Load json from {}", jsonPath);
        JsonReader reader = getJsonReader(jsonPath);
        Gson gson = new Gson();
        return gson.fromJson(reader, type);
    }
}
