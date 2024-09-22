package com.agest.model;

import lombok.Getter;

public enum PanelType {
    CHARTS("Charts"),
    INDICATORS("Indicators"),
    REPORTS("Reports"),
    HEAT_MAPS("Heat Maps");

    @Getter
    private final String name;

    PanelType(String name) {
        this.name = name;
    }
}
