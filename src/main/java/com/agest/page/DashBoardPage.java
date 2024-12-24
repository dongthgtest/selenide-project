package com.agest.page;

import com.agest.model.sele2.GlobalSetting;
import com.agest.model.sele2.Page;
import com.agest.model.sele2.User;
import com.agest.utils.AlertUtils;
import com.agest.utils.Constants;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DashBoardPage extends BasePage {
    private final SelenideElement dashBoardContent = $("#ccontent");
    private final SelenideElement choosePanelButton = $("#btnChoosepanel");
    private final SelenideElement logoutButton = $x("//ul[@class='head-menu']//a[text()='Logout']");
    private final SelenideElement globalSettingButton = $(".mn-setting");
    private final SelenideElement deletePageButton = $(".delete");
    private final SelenideElement userLink = $x("//a[@href='#Welcome']");
    private final SelenideElement administerLink = $x("//a[@href='#Administer']");
    private final SelenideElement panelsButton = $x("//a[text()='Panels']");
    private final SelenideElement dataProfileButton = $x("//a[text()='Data Profiles']");
    private final SelenideElement parentPage = $(".active.haschild");
    private final String dynamicPage = "//div[@id='main-menu']//a[text()='%s']";
    private final String dynamicChildPage = "//div[@id='main-menu']//li[@class='active haschild']//a[text()='%s']";

    @Step("Should user is login successful")
    public void shouldUserLoginSuccessful(User user) {
        dashBoardContent.should(appear);
        userLink.shouldHave(text(user.getUsername()));
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

    @Step("Open {globalSetting.name}")
    private void selectGlobalSetting(GlobalSetting globalSetting) {
        String dynamicGlobalSetting = "//a[@class='add' and text()='%s']";
        globalSettingButton.hover();
        $x(String.format(dynamicGlobalSetting, globalSetting.getName())).click();
    }

    @Step("Open {page.pageName}")
    public void openPage(Page page) {
        openPage(page.getPageName());
    }

    @Step("Open {pageName}")
    private void openPage(String pageName) {
        $x(String.format(dynamicPage, pageName)).click();
    }

    @Step("Open child page")
    public void openChildPage(Page childPage, Page parentPage) {
        hoverParentPage(parentPage);
        $x(String.format(dynamicChildPage, childPage.getPageNameFormat())).click();
    }

    @Step("Should child page deleted")
    public void shouldChildPageDeleted(Page childPage, Page parentPage) {
        hoverParentPage(parentPage);
        $x(String.format(dynamicChildPage, childPage.getPageName())).shouldBe(disappear);
    }

    @Step("Hover to parent page")
    private void hoverParentPage(Page parentPage) {
        $x(String.format(dynamicPage, parentPage.getPageNameFormat())).hover();
    }

    @Step("Page should visible")
    public void shouldPageVisible(Page page) {
        $x(String.format(dynamicPage, page.getPageNameFormat())).shouldBe(visible, Constants.SHORT_WAIT);
    }

    @Step("Child page should visible")
    public void shouldChildPageVisible(Page childPage) {
        parentPage.hover();
        $x(String.format(dynamicChildPage, childPage.getPageNameFormat())).shouldBe(visible, Constants.SHORT_WAIT);
    }

    @Step("Delete page completely")
    public void deletePage() {
        clickDeletePage();
        AlertUtils.accept();
    }

    @Step("Click delete page")
    public void clickDeletePage() {
        globalSettingButton.hover();
        deletePageButton.click();
    }

    @Step("Page should disappear")
    public void shouldPageDisappear(Page page) {
        $x(String.format(dynamicPage, page.getPageName())).shouldBe(disappear);
    }

    @Step("Delete button should disappears")
    public void shouldDeleteButtonDisappears() {
        deletePageButton.shouldBe(disappear);
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

    @Step("Open data profiles")
    public void openDataProfiles() {
        administerLink.hover();
        dataProfileButton.click();
    }

    public void shouldAllOtherElementBlockedOrLocked(String expectedUrl) {
        List<String> errorMessages = getErrorMessages(expectedUrl);
        Assert.assertTrue(errorMessages.isEmpty(), errorMessages.toString());
    }

    @Step("Verify all other elements are blocked/locked")
    private List<String> getErrorMessages(String expectedUrl) {
        List<String> errorMessages = new ArrayList<>();
        ElementsCollection menuElements = $$x("//div[@id='main-menu']//li/a");
        menuElements.forEach(element -> {
            if (!isElementsBlocked(element)) {
                errorMessages.add("This element is not blocked: " + element);
            }
        });
        ElementsCollection headerElements = $$x("//ul[@class='head-menu']//li/a");
        headerElements.forEach(element -> {
            if (!isElementLocked(element, expectedUrl)) {
                errorMessages.add("This element is not locked: " + element);
            }
        });
        return errorMessages;
    }

    private boolean isElementLocked(SelenideElement element, String expectedUrl) {
        element.click();
        return WebDriverRunner.getWebDriver().getCurrentUrl().equals(expectedUrl) && isPopupDisplayed();
    }

    private boolean isPopupDisplayed() {
        return $("#ui-dialog-title-div_panelPopup").isDisplayed();
    }
}
