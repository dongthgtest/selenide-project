package com.agest.page.vietjet;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;

public class BasePage {
    private final SelenideElement notificationPopup = $("#preview-notification-frame");
    private final SelenideElement notificationCloseButton = $("#NC_CTA_TWO");

    protected SelenideElement getRippleButton(String label) {
        return $x("//button[descendant::span[text()='%s']]".formatted(label));
    }

    @Step("Close notification popup if it is visible")
    public void closeNotificationPopup() {
        if (notificationPopup.is(exist, Duration.ofSeconds(5))) {
            switchTo().frame(notificationPopup);
            notificationCloseButton.click();
            switchTo().defaultContent();
        }
    }
}
