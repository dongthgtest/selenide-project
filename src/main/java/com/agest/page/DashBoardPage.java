package com.agest.page;

import com.agest.model.GlobalSetting;
import com.agest.model.User;
import com.agest.utils.Constants;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DashBoardPage extends BasePage {
    private final SelenideElement dashBoardContent = $("#ccontent");
    private final SelenideElement logoutButton = $x("//ul[@class='head-menu']//a[text()='Logout']");
    private final SelenideElement globalSettingButton = $(".mn-setting");
    private final SelenideElement deletePageButton = $(".delete");
    private final String dynamicPage = "//div[@id='main-menu']//a[text()='%s']";
    private final String dynamicUserLink = "//div[@id='header']//a[text()='%s']";


    @Step("Should user {username} is login successful")
    public void shouldUserLoginSuccessful(String username) {
        dashBoardContent.should(appear);
        $x(String.format(dynamicUserLink, username)).shouldBe(visible);
    }

    @Step("Log out")
    public void logOut(User user) {
        SelenideElement userLink = $x(String.format(dynamicUserLink, user.getUsername()));
        userLink.hover();
        logoutButton.click();
    }

    @Step("Open {option}")
    public void selectGlobalSetting(GlobalSetting globalSetting) {
        String dynamicGlobalSetting = "//a[@class='add' and text()='%s']";
        SelenideElement settingOption = $x(String.format(dynamicGlobalSetting, globalSetting.getName()));
        hoverGlobalSetting();
        settingOption.click();
    }

    @Step("Hover to global setting")
    public void hoverGlobalSetting() {
        hover(globalSettingButton);
    }

    @Step("Open page")
    public void openPage(String pageName) {
        SelenideElement page = $x(String.format(dynamicPage, pageName));
        page.click();
    }

    @Step("Page should visible")
    public void shouldPageVisible(String pageName) {
        SelenideElement page = $x(String.format(dynamicPage, pageName));
        page.shouldBe(visible, Constants.SHORT_WAIT);
    }

    @Step("Delete page")
    public void deletePage() {
        deletePageButton.click();
    }

    @Step("Page should disappear")
    public void shouldPageDisappear(String pageName) {
        SelenideElement page = $x(String.format(dynamicPage, pageName));
        page.shouldBe(disappear);
    }
}
