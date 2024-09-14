package com.agest.listener;

import io.qameta.allure.listener.TestLifecycleListener;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;


public class AllureTestListener implements TestLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(AllureTestListener.class);

    @Override
    public void beforeTestStop(TestResult result) {
        if (result.getStatus() != null &&
                (result.getStatus().equals(Status.BROKEN) || result.getStatus().equals(Status.FAILED))) {
            logger.info("Test case \"{}\" has been \"{}\". Take a screenshot", result.getFullName(),
                    result.getStatus().value());
            try {
                byte[] buf = Selenide.takeScreenShot(OutputType.BYTES);
                if (buf != null) {
                    ByteArrayInputStream input = new ByteArrayInputStream(buf);
                    Allure.attachment(UUID.randomUUID().toString(), input);
                }
            } catch (Exception e) {
                logger.error("An error occurs when adding screenshot to report: \n{}", e.getMessage());
            }
        }
    }
}
