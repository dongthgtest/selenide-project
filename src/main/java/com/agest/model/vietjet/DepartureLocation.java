package com.agest.model.vietjet;

import lombok.Getter;

@Getter
public enum DepartureLocation {
    HO_CHI_MINH("Ho Chi Minh"),
    HA_NOI("Ha Noi");

    private final String name;

    DepartureLocation(String name) {
        this.name = name;
    }
}
