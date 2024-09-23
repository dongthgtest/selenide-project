package com.agest.page;

import com.agest.model.Panel;
import com.agest.utils.AlertUtils;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class PanelManagerPage extends BasePage {

    @Step("Delete panel")
    public void deletePanel(Panel panel) {
        String dynamicPanelDeleteButton = "//td[input[@id='chkDelPanel']]/following-sibling::td[a[text()='%s']]/following-sibling::td/a[text()='Delete']";
        $x(String.format(dynamicPanelDeleteButton, panel.getDisplayName())).click();
        AlertUtils.accept();
    }
}
