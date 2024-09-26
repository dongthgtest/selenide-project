package com.agest.config;

import com.agest.utils.Constants;
import lombok.Data;

@Data
public class TestConfig {
    private String browser;
    private String remote;
    private String maxRetry;
    private boolean headless;
    private String reportFolder;
    private boolean startMaximized;
    private long timeout;
    private static TestConfig instance = null;

    private TestConfig() {
        this.browser = "chrome";
        this.remote = null;
        this.headless = false;
        this.reportFolder = "allure-results";
        this.startMaximized = true;
        this.timeout = 5000;
    }

    public static TestConfig getInstance() {
        if (instance == null) {
            instance = new TestConfig();
        }
        return instance;
    }

    public String remote() {
        return Constants.GRID_HUB_URL;
    }
}
