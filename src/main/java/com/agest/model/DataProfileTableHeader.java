package com.agest.model;

import lombok.Getter;

@Getter
public enum DataProfileTableHeader {
    DATA_PROFILE("Data Profile", "2"),
    ITEM_TYPE("Item type", "3"),
    RELATED_DATA("Related data", "4"),
    CREATED_BY("Created by", "5"),
    CREATION_DATE("Creation date", "6"),
    ACTION("Action", "7");

    private final String name;
    private final String columnIndex;

    DataProfileTableHeader(String name, String columnIndex) {
        this.name = name;
        this.columnIndex = columnIndex;
    }
}
