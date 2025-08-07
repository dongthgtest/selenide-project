package com.agest.model.agoda;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

@Data
@Builder
public class FilterHotelCriteria {
    private Pair<Integer, Integer> priceRange;
    private String destination;
    private Integer rating;
    private String facility;
}
