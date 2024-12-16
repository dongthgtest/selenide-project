package com.agest.page;

import com.agest.model.sele2.Panel;
import com.agest.utils.AlertUtils;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class PanelManagerPage extends BasePage {
    private final SelenideElement addNewButton = $x("//div[@id='ccontent']//a[text()='Add New']");

    @Step("Delete panel")
    public void deletePanel(Panel panel) {
        String dynamicPanelDeleteButton = "//td[input[@id='chkDelPanel']]/following-sibling::td[a[text()='%s']]" +
                "/following-sibling::td/a[text()='Delete']";
        $x(String.format(dynamicPanelDeleteButton, panel.getDisplayName())).click();
        AlertUtils.accept();
    }

    @Step("Click add new")
    public void addNewPanel() {
        addNewButton.click();
    }

    @Step("Verify Add New button is blocked")
    public void shouldAddNewButtonBlocked() {
        isElementsBlocked(addNewButton);
    }
}
