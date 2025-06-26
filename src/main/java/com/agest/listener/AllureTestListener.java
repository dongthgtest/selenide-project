package com.agest.listener;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class AllureTestListener extends AllureSelenide implements ITestListener {
    public AllureTestListener() {
        SelenideLogger.addListener("AllureSelenide", this);
    }

    @Override
    public void afterEvent(LogEvent event) {
        if (event.getStatus().equals(LogEvent.EventStatus.FAIL)) {
            Allure.addAttachment(
                    "Screenshot on failure",
                    new ByteArrayInputStream(((TakesScreenshot) WebDriverRunner.getWebDriver())
                            .getScreenshotAs(OutputType.BYTES))
            );
        }
        super.afterEvent(event);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Allure.addAttachment(
                    "Screenshot on test failure",
                    new ByteArrayInputStream(((TakesScreenshot) WebDriverRunner.getWebDriver())
                            .getScreenshotAs(OutputType.BYTES))
            );
        }
    }
}
