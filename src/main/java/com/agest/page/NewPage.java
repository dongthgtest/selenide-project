package com.agest.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class NewPage extends BasePage {
    private final SelenideElement pageNameInput = $("#name");
    private final SelenideElement publicCheckBox = $("#ispublic");
    private final SelenideElement okButton = $("#OK");

    @Step("Input {pageName} to page name field")
    public void inputPageNameField(String pageName) {
        pageNameInput.setValue(pageName);
    }

    @Step("Check Public checkbox")
    public void checkPublicCheckBox() {
        publicCheckBox.click();
    }

    @Step("Click OK button")
    public void clickOkButton() {
        okButton.click();
    }
}
