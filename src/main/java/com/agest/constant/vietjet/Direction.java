package com.agest.constant.vietjet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Direction {
    DEPARTURE("Departure"),
    RETURN("Return");

    private final String name;
}
