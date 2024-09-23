package com.agest.model;

import lombok.Getter;

@Getter
public enum AlertMessages {
    CONFIRM_REMOVE_PAGE("Are you sure you want to remove this page?"),
    WARNING_REMOVE_PAGE("Cannot delete page '%s' since it has child page(s).");

    private final String message;

    AlertMessages(String message) {
        this.message = message;
    }
}
