package com.agest.page;

import com.agest.model.Panel;
import com.agest.model.Series;
import com.agest.utils.Constants;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CreatePanelPage extends BasePage {
    private final SelenideElement displayNameInput = $("#txtDisplayName");
    private final SelenideElement seriesDropDown = $("#cbbSeriesField");
    private final SelenideElement panelConfigHeader = $("#ui-dialog-title-div_panelConfigurationDlg");
    private final SelenideElement okButton = $x("//div[@role='dialog' and contains(@style,'display: block')]//input[@id='OK']");
    private final String dynamicSeries = "//select[@id='cbbSeriesField']";

    @Step("Enter display name {displayName}")
    private void enterDisplayName(String displayName) {
        displayNameInput.setValue(displayName);
    }

    @Step("Select random series")
    private void selectSeries(Series s) {
        seriesDropDown.selectOption("  " + s.getValue());
    }

    public void createPanel(Panel panelData) {
        enterDisplayName(panelData.getDisplayName());
        selectSeries(panelData.getSeries());
        okButton.click();
        waitForVisible(panelConfigHeader, Constants.SHORT_WAIT);
        okButton.click();
    }
}
