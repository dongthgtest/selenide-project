package com.agest.utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Alert;

public class AlertUtils {
    private static final Alert alert = WebDriverRunner.getWebDriver().switchTo().alert();

    public static String getAlertContent() {
        return alert.getText();
    }
}
