package com.agest.utils;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;

public class Actions {
    public static boolean isElementDisplayed(SelenideElement element, Duration timeout) {
        try {
            element.should(visible, timeout);
            return element.isDisplayed();
        } catch (ElementNotFound e) {
            return false;
        }
    }
}
