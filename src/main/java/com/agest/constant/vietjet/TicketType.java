package com.agest.constant.vietjet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TicketType {
    ONE_WAY("One Way"),
    ROUND_TRIP("Round Trip");

    private final String name;
}
