package com.agest.page;

import com.agest.model.sele2.Page;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class NewPage extends BasePage {
    private final SelenideElement pageNameInput = $("#name");
    private final SelenideElement publicCheckBox = $("#ispublic");
    private final SelenideElement parentPageDropDown = $("#parent");

    @Step("Input {pageName} to page name field")
    private void inputPageNameField(String pageName) {
        pageNameInput.setValue(pageName);
    }

    @Step("Check Public checkbox")
    private void setPagePublic() {
        publicCheckBox.click();
    }

    @Step("Create new page")
    public void createNewPage(Page page) {
        inputPageNameField(page.getPageName());
        setPagePublic();
        clickOkButton();
    }

    @Step("Create new page")
    public void createNewChildPage(Page childPage, Page parentPage) {
        inputPageNameField(childPage.getPageName());
        parentPageDropDown.selectOption(parentPage.getPageNameFormat());
        clickOkButton();
    }
}
