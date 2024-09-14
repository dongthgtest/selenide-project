package com.agest.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TAHomePage {
    private final SelenideElement usernameLabel = $("a[href='#Welcome']");

    @Step("Verify user {username} is login successful")
    public boolean isLoginSuccessful(String username) {
        usernameLabel.should(exist);
        return usernameLabel.shouldHave(text("username")).isDisplayed();
    }
}
