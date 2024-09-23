package com.agest.page;

import com.agest.utils.AlertUtils;
import com.agest.utils.Constants;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class BasePage {

    @Step("Click OK button")
    protected void clickOkButton() {
        $("#OK").click();
    }

    @Step("Accept alert")
    public void acceptAlert() {
        AlertUtils.accept();
    }

    protected void waitForVisible(SelenideElement element) {
        element.shouldBe(visible, Constants.SHORT_WAIT);
    }
}
