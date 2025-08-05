package com.agest.model.agoda;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

@Data
@Builder
public class FilterHotelCriteria {
    private Pair<Integer, Integer> priceRange; // Price range in VND
    private String rating; // Rating of the hotel (e.g., "3 stars", "4 stars", etc.)
    private String facility; // Specific facility to filter by (e.g., "pool", "gym", etc.)
}
