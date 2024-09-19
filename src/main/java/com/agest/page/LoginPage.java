package com.agest.page;

import com.agest.model.User;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $(".btn-login");
    private final SelenideElement listRepository = $("#repository");
    private final String dynamicRepository = "//option[@value='%s']";

    @Step("Login with username = {user.username}, password = {user.password}")
    public void loginUser(User user) {
        inputUsername(user.getUsername());
        inputPassword(user.getPassword());
        clickLogin();
    }

    @Step("Input with username: {username}")
    public void inputUsername(String username) {
        usernameInput.should(exist);
        usernameInput.setValue(username);
    }

    @Step("Input with password: {password}")
    public void inputPassword(String password) {
        passwordInput.should(exist);
        passwordInput.setValue(password);
    }

    @Step("Click login")
    public void clickLogin() {
        loginButton.click();
    }

    @Step("Switch to {repo}")
    public void switchTo(String repo) {
        SelenideElement targetRepo = $x(String.format(dynamicRepository, repo));
        targetRepo.click();
    }
}
