package com.agest.model.sele2;

import lombok.Getter;
import org.apache.commons.lang3.RandomUtils;

public enum Series {
    NAME("Name"),
    LOCATION("Location"),
    DESCRIPTION("Description"),
    REVISION_TIMESTAMP("Revision Timestamp"),
    ASSIGNED_USER("Assigned user"),
    STATUS("Status"),
    LAST_UPDATE_DATE("Last update date"),
    LAST_UPDATED_BY("Last updated by"),
    CREATION_DATE("Creation date"),
    CREATED_BY("Created by"),
    NOTES("Notes"),
    URL("URL");

    @Getter
    private final String value;

    Series(String value) {
        this.value = value;
    }

    public static Series getRandomSeries() {
        return Series.values()[RandomUtils.nextInt(0, values().length)];
    }
}
