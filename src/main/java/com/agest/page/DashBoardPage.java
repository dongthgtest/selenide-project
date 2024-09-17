package com.agest.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DashBoardPage extends BasePage {
    private final SelenideElement dashBoardContent = $("#ccontent");
    private final SelenideElement globalSetting = $(".mn-setting");
//    private final SelenideElement addPage = $(".add").shouldHave(text("Add Page"));
    private final SelenideElement logoutButton = $x("//ul[@class='head-menu']//a[text()='Logout']");
    private final String dynamicUserLink = "//div[@id='header']//a[text()='%s']";


    @Step("Should user {username} is login successful")
    public void shouldUserLoginSuccessful(String username) {
        dashBoardContent.should(appear);
        $x(String.format(dynamicUserLink, username)).shouldBe(visible);
    }

    @Step("Log out")
    public void logOut(String username) {
        SelenideElement userLink = $x(String.format(dynamicUserLink, username));
        userLink.hover();
        logoutButton.click();
    }

//    @Step("Click Add Page")
//    public void clickAddPage() {
//        hover(globalSetting);
//        addPage.click();
//    }
}
