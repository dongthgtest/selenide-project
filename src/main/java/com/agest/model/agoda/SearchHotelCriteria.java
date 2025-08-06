package com.agest.model.agoda;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SearchHotelCriteria {
    private String destination;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    @Builder.Default
    private int adultQuantity = 2;
    @Builder.Default
    private int roomQuantity = 1;
}
