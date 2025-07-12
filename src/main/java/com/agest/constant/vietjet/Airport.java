package com.agest.constant.vietjet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Airport {
    HO_CHI_MINH("Ho Chi Minh", "SGN"),
    HA_NOI("Ha Noi", "HAN");

    private final String name;
    private final String code;

    @Override
    public String toString() {
        return String.format("%s (%s)", name, code);
    }
}
