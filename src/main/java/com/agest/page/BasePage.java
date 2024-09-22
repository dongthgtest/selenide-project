package com.agest.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class BasePage {

    @Step("Click OK button")
    protected void clickOkButton() {
        SelenideElement okButton = $("#OK");
        okButton.click();
    }

    protected void waitForVisible(SelenideElement element, Duration timeout) {
        element.shouldBe(visible, timeout);
    }
}
