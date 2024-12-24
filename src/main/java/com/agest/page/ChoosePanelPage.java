package com.agest.page;

import com.agest.model.sele2.Panel;
import com.agest.model.sele2.PanelType;
import com.agest.utils.Constants;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

public class ChoosePanelPage extends DashBoardPage {
    private final SelenideElement hideButton = $("#btnHidePanel");

    @Step("Wait for Choose Panels page visible")
    public void waitForChoosePanelsPageVisible() {
        hideButton.shouldBe(visible, Constants.SHORT_WAIT);
    }

    private ElementsCollection getTableItems(String tableName) {
        String dynamicTableItem = "//div[@class='pitem']/div[text()='%s']/following-sibling::table//li/a";
        return $$x(String.format(dynamicTableItem, tableName));
    }

    @Step("Get list panels of {panelType}")
    private List<Panel> getPanelsByType(PanelType panelType) {
        ElementsCollection items = getTableItems(panelType.getName());
        return items.stream().map(item -> new Panel(item.getText()
                        .replace(" ", " ")))
                .collect(Collectors.toList());
    }

    public List<Panel> getChartsPanel() {
        return getPanelsByType(PanelType.CHARTS);
    }

    public List<Panel> getReportsPanel() {
        return getPanelsByType(PanelType.REPORTS);
    }

    public List<Panel> getIndicatorsPanel() {
        return getPanelsByType(PanelType.INDICATORS);
    }

    public List<Panel> getHeatMapsPanel() {
        return getPanelsByType(PanelType.HEAT_MAPS);
    }
}
