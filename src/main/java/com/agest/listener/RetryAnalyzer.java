package com.agest.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = getMaxRetryCount();

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }

    private static int getMaxRetryCount() {
        String retryCountStr = System.getenv("MAX_RETRY_COUNT");
        if (retryCountStr == null) {
            retryCountStr = System.getProperty("maxRetryCount", "3");
        }
        return Integer.parseInt(retryCountStr);
    }
}
