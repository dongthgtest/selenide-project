package com.agest.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DashBoardPage {
    private final SelenideElement dashBoardContent = $("#ccontent");

    @Step("Should user {username} is login successful")
    public void shouldUserLoginSuccessful(String username) {
        dashBoardContent.should(appear);
        SelenideElement userLink = $x("//div[@id='header']//a[text()='" + username + "']").shouldBe(visible);
    }
}
