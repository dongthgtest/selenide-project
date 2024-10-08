package com.agest.utils;

import java.time.Duration;

public class Constants {
    // url
    public static final String TA_DASHBOARD = "http://localhost/TADashboard/login.jsp";
    public static final String TIKI_URL = "https://tiki.vn/";
    public static final String GRID_HUB_URL = "http://192.168.1.10:4444/wd/hub";

    //Time out
    public static final Duration SHORT_WAIT = Duration.ofSeconds(3);
    public static final Duration DISPLAY_TIMEOUT = Duration.ofSeconds(5);

    // Relative path
    public static final String PRESET_DATA_PROFILES = "src/test/resources/test_data/dataProfiles.json";

    public static final String TEST_REPO = "Test";
}
