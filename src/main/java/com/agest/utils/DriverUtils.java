package com.agest.utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.codeborne.selenide.Selenide.sleep;

public class DriverUtils {

    private static final Logger logger = LoggerFactory.getLogger(DriverUtils.class);
    private static final int DEFAULT_TIMEOUT_SECONDS = 10;
    private static final int POLL_INTERVAL_MS = 500;

    /**
     * Switch to window by index with proper error handling
     * @param windowIndex The index of the window to switch to (0-based)
     * @param timeoutSeconds Maximum time to wait for the window
     * @return true if successfully switched, false otherwise
     */
    public static boolean switchToWindow(int windowIndex, int timeoutSeconds) {
        try {
            WebDriver driver = WebDriverRunner.getWebDriver();

            // Wait for the window to be available
            if (waitForWindowCount(windowIndex + 1, timeoutSeconds)) {
                List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());

                if (windowIndex < windowHandles.size()) {
                    String targetWindow = windowHandles.get(windowIndex);
                    driver.switchTo().window(targetWindow);
                    logger.info("Successfully switched to window index: {}", windowIndex);
                    return true;
                } else {
                    logger.warn("Window index {} not found. Available windows: {}", windowIndex, windowHandles.size());
                    return false;
                }
            } else {
                logger.warn("Timeout waiting for window index {} to be available", windowIndex);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error switching to window index {}: {}", windowIndex, e.getMessage());
            return false;
        }
    }

    /**
     * Switch to window by index with default timeout
     * @param windowIndex The index of the window to switch to (0-based)
     * @return true if successfully switched, false otherwise
     */
    public static boolean switchToWindow(int windowIndex) {
        return switchToWindow(windowIndex, DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Switch to the latest/newest window
     * @param timeoutSeconds Maximum time to wait for new window
     * @return true if successfully switched, false otherwise
     */
    public static boolean switchToLatestWindow(int timeoutSeconds) {
        try {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Set<String> currentWindows = driver.getWindowHandles();

            if (currentWindows.size() > 1) {
                List<String> windowHandles = new ArrayList<>(currentWindows);
                String latestWindow = windowHandles.get(windowHandles.size() - 1);
                driver.switchTo().window(latestWindow);
                logger.info("Successfully switched to latest window");
                return true;
            } else {
                logger.warn("No additional windows found to switch to");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error switching to latest window: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Switch to the latest window with default timeout
     */
    public static boolean switchToLatestWindow() {
        return switchToLatestWindow(DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Wait for a specific number of windows to be available
     * @param expectedWindowCount The number of windows to wait for
     * @param timeoutSeconds Maximum time to wait
     * @return true if the expected number of windows is available, false otherwise
     */
    public static boolean waitForWindowCount(int expectedWindowCount, int timeoutSeconds) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        long startTime = System.currentTimeMillis();
        long timeout = timeoutSeconds * 1000L;

        while (System.currentTimeMillis() - startTime < timeout) {
            Set<String> windows = driver.getWindowHandles();
            if (windows.size() >= expectedWindowCount) {
                logger.info("Found {} windows as expected", windows.size());
                return true;
            }
            sleep(POLL_INTERVAL_MS);
        }

        Set<String> windows = driver.getWindowHandles();
        logger.warn("Timeout waiting for {} windows. Current window count: {}", expectedWindowCount, windows.size());
        return false;
    }

    /**
     * Wait for a new window to open after performing an action
     * @param action The action that should trigger a new window
     * @param timeoutSeconds Maximum time to wait for new window
     * @return true if new window opened and switched successfully, false otherwise
     */
    public static boolean performActionAndSwitchToNewWindow(Runnable action, int timeoutSeconds) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        Set<String> initialWindows = driver.getWindowHandles();
        int initialWindowCount = initialWindows.size();

        logger.info("Performing action that may open new window. Current windows: {}", initialWindowCount);

        // Perform the action
        action.run();

        // Wait for new window
        if (waitForWindowCount(initialWindowCount + 1, timeoutSeconds)) {
            return switchToLatestWindow();
        } else {
            logger.info("No new window opened after action. Continuing with current window.");
            return true; // Return true because the action completed, just no new window
        }
    }
}