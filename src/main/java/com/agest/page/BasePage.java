package com.agest.page;

import com.agest.utils.AlertUtils;
import com.agest.utils.Constants;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.UIAssertionError;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
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

    protected boolean isElementsBlocked(SelenideElement element) {
        try {
            element.click();
        } catch (UIAssertionError e) {
            if (e.getMessage().contains("is not clickable at point")) {
                log.warn("The element {} is blocked by another element.", element);
            } else {
                log.error(e.getMessage());
                return false;
            }
        }
        return true;
    }

    protected boolean isUrlChanged(String url) {
        return !WebDriverRunner.getWebDriver().getCurrentUrl().equals(url);
    }
}
