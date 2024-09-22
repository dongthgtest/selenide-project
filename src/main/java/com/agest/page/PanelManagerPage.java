package com.agest.page;

import com.agest.model.Panel;
import com.agest.utils.AlertUtils;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class PanelManagerPage extends BasePage {
    private final String dynamicPanelDeleteButton = "//td[input[@id='chkDelPanel']]/following-sibling::td[a[text()='%s']]/following-sibling::td/a[text()='Delete']";

    @Step("Delete panel")
    public void deletePanel(Panel panel) {
        SelenideElement deleteButton = $x(String.format(dynamicPanelDeleteButton, panel.getDisplayName()));
        deleteButton.click();
        AlertUtils.accept();
    }
}
