package com.agest.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class BasePage {
    @Step("Hover to element")
    public void hover(SelenideElement element) {
        element.hover();
    }
}
