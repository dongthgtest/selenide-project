package com.agest.utils;

import com.codeborne.selenide.SelenideWait;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AlertUtils {
    public static String getAlertContent() {
        SelenideWait selenideWait = new SelenideWait(WebDriverRunner.getWebDriver(), 5000, 200);
        selenideWait.until(ExpectedConditions.alertIsPresent());
        Alert alert = WebDriverRunner.getWebDriver().switchTo().alert();
        return alert.getText();
    }
}
