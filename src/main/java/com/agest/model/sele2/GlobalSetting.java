package com.agest.model.sele2;

import lombok.Getter;

public enum GlobalSetting {
    ADD_PAGE("Add Page"),
    CREATE_PROFILE("Create Profile"),
    CREATE_PANEL("Create Panel");

    @Getter
    private final String name;

    GlobalSetting(String name) {
        this.name = name;
    }
}
