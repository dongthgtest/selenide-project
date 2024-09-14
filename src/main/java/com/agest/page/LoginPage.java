package com.agest.page;

import com.agest.model.User;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $(".btn-login");
    private final SelenideElement mainHeader = $("#header");

    @Step("Login with username = {user.username}, password = {user.password}")
    public void loginUser(User user) {
        usernameInput.should(exist);
        usernameInput.setValue(user.getUsername());
        passwordInput.setValue(user.getPassword());
        loginButton.click();
        mainHeader.should(exist);
    }

    @Step("Verify login successfully")
    public boolean isLoggingSuccessful() {
        return mainHeader.isDisplayed();
    }
}
