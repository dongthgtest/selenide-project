package com.agest.model.vietjet;

import com.agest.constant.vietjet.Airport;
import com.agest.constant.vietjet.TicketType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SearchFlightCriteria {
    private Airport departureAirport;
    private Airport arrivalAirport;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private TicketType ticketType;
    @Builder.Default
    private int numberOfAdults = 1;
    @Builder.Default
    private int numberOfChildren = 0;
    @Builder.Default
    private int numberOfInfants = 0;
    private String promoCode;
    private boolean findCheapest;

    /**
     * Check if the search criteria is for a round-trip ticket.
     *
     * @return
     */
    public boolean isRoundTrip() {
        return ticketType == TicketType.ROUND_TRIP;
    }
}
