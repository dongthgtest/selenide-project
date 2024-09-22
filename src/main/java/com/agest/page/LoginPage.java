package com.agest.page;

import com.agest.model.User;
import com.agest.utils.Constants;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends BasePage {
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $(".btn-login");
    private final SelenideElement listRepository = $("#repository");
    private final String dynamicRepository = "//option[@value='%s']";

    @Step("Login with username = {user.username}, password = {user.password}")
    public void login(User user) {
        inputUsername(user.getUsername());
        inputPassword(user.getPassword());
        clickLogin();
    }

    @Step("Login with username = {user.username}, password = {user.password} on {repository}")
    public void login(User user, String repository) {
        switchRepo(repository);
        login(user);
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
    private void switchRepo(String repo) {
        listRepository.click();
        SelenideElement targetRepo = $x(String.format(dynamicRepository, repo));
        waitForVisible(targetRepo, Constants.SHORT_WAIT);
        targetRepo.click();
    }
}
