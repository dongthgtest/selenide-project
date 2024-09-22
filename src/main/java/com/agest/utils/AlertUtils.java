package com.agest.utils;

import com.codeborne.selenide.SelenideWait;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AlertUtils {
    public static void waitForAlertDisplayed(int timeoutMilliseconds) {
        SelenideWait selenideWait = new SelenideWait(WebDriverRunner.getWebDriver(), timeoutMilliseconds, 200);
        selenideWait.until(ExpectedConditions.alertIsPresent());
    }

    public static String getAlertContent() {
        waitForAlertDisplayed(5000);
        Alert alert = WebDriverRunner.getWebDriver().switchTo().alert();
        return alert.getText();
    }

    public static void accept() {
        Alert alert = WebDriverRunner.getWebDriver().switchTo().alert();
        alert.accept();
    }
}
