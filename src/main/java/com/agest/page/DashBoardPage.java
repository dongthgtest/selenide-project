package com.agest.page;

import com.agest.model.GlobalSetting;
import com.agest.model.Page;
import com.agest.utils.AlertUtils;
import com.agest.utils.Constants;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DashBoardPage extends BasePage {
    private final SelenideElement dashBoardContent = $("#ccontent");
    private final SelenideElement choosePanelButton = $("#btnChoosepanel");
    private final SelenideElement logoutButton = $x("//ul[@class='head-menu']//a[text()='Logout']");
    private final SelenideElement globalSettingButton = $(".mn-setting");
    private final SelenideElement deletePageButton = $(".delete");
    private final SelenideElement userLink = $x("//a[@href='#Welcome']");
    private final SelenideElement administerLink = $x("//a[@href='#Administer']");
    private final SelenideElement panelsButton = $x("//a[text()='Panels']");
    private final String dynamicPage = "//div[@id='main-menu']//a[text()='%s']";

    @Step("Should user {username} is login successful")
    public void shouldUserLoginSuccessful(String username) {
        dashBoardContent.should(appear);
        userLink.shouldHave(text(username));
    }

    @Step("Log out")
    public void logOut() {
        userLink.hover();
        logoutButton.click();
    }

    public void selectGlobalSettingAddPage() {
        selectGlobalSetting(GlobalSetting.ADD_PAGE);
    }

    public void selectGlobalSettingCreatePanel() {
        selectGlobalSetting(GlobalSetting.CREATE_PANEL);
    }

    @Step("Open {option}")
    private void selectGlobalSetting(GlobalSetting globalSetting) {
        String dynamicGlobalSetting = "//a[@class='add' and text()='%s']";
        SelenideElement settingOption = $x(String.format(dynamicGlobalSetting, globalSetting.getName()));
        globalSettingButton.hover();
        settingOption.click();
    }

    @Step("Open {page.pageName}")
    public void openPage(Page page) {
        SelenideElement targetPage = $x(String.format(dynamicPage, page.getPageName()));
        targetPage.click();
    }

    @Step("Page should visible")
    public void shouldPageVisible(Page page) {
        SelenideElement targetPage = $x(String.format(dynamicPage, page.getPageNameFormat()));
        targetPage.shouldBe(visible, Constants.SHORT_WAIT);
    }

    @Step("Delete page")
    public void deletePage() {
        globalSettingButton.hover();
        deletePageButton.click();
        AlertUtils.accept();
    }

    @Step("Page should disappear")
    public void shouldPageDisappear(Page page) {
        SelenideElement targetPage = $x(String.format(dynamicPage, page.getPageName()));
        targetPage.shouldBe(disappear);
    }

    @Step("Open choose panel")
    public void openChoosePanel() {
        choosePanelButton.click();
    }

    @Step("Open panel manager")
    public void openPanelManager() {
        administerLink.hover();
        panelsButton.click();
    }
}
