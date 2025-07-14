package com.agest.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

@Slf4j
/**
 * I18n class for handling internationalization.
 * It loads language files from the resources directory and provides methods to get translations.
 */
public class I18n {
    private Map<String, Object> data;
    @Getter
    private Locale currentLang;

    @Getter
    private final static I18n instance = new I18n();
    private final static Locale ENGLISH = Locale.ENGLISH;
    private final static Locale VIETNAMESE = Locale.of("vi");

    private I18n() {
    }

    public void loadLanguage(Locale locale) {
        Yaml yaml = new Yaml();
        String yamlFile = "i18n/%s.yaml".formatted(locale.getLanguage());
        log.info("Reading yaml {} file", yamlFile);
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(yamlFile)) {
            if (in == null) {
                throw new IllegalArgumentException("YAML file not found: " + yamlFile);
            }
            data = yaml.load(in);
            currentLang = locale;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML file", e);
        }
    }

    public boolean isEnglish() {
        return currentLang.equals(ENGLISH);
    }

    public boolean isVietnamese() {
        return currentLang.equals(VIETNAMESE);
    }

    /**
     * Get translation by path.
     * Example: "homePage.title"
     *
     * @param path the path to the translation
     * @return the translation string
     */
    public String t(String path) {
        log.debug("Get path: {}", path);
        return (String) get(path);
    }

    protected Object get(String path) {
        String[] keys = path.split("\\.");
        Object current = data;
        for (String key : keys) {
            if (!(current instanceof Map)) {
                return null;
            }
            current = ((Map<?, ?>) current).get(key);
            if (current == null) {
                return null;
            }
        }
        return current;
    }
}
