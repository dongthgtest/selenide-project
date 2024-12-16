package com.agest.model.sele2;

import lombok.Getter;

@Getter
public enum PanelType {
    CHARTS("Charts", "Charts panels not displayed as expected!"),
    INDICATORS("Indicators", "Indicators panels not displayed as expected"),
    REPORTS("Reports", "Reports panels not displayed as expected"),
    HEAT_MAPS("Heat Maps", "Heat Maps panels not displayed as expected");

    private final String name;
    private final String errorMessage;

    PanelType(String name, String errorMessage) {
        this.name = name;
        this.errorMessage = errorMessage;
    }
}
