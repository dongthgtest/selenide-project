package com.agest.page.vietjet;

import com.agest.constant.vietjet.Airport;
import com.agest.constant.vietjet.Direction;
import com.agest.model.vietjet.SearchFlightCriteria;
import com.agest.page.IPage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.partialText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

public class PassengerInformationPage implements IPage {
    private final SelenideElement titleLabel = $("h3.MuiTypography-h3");
    private final ElementsCollection ticketInfoHeaders = $$x("//div[p[@variantlg='h3']]");

    public void verifyPassengerInformationPageDisplayed() {
        titleLabel.shouldHave(text(i18n.t("vietjet.passengerInfo.title")));
    }

    public void verifyTicketInformation(SearchFlightCriteria criteria) {
        verifyDepartureFlight(criteria);
        if (criteria.isRoundTrip()) {
            verifyReturnFlight(criteria);
        }
    }

    @Step("Verify departure flight information for criteria: {criteria}")
    public void verifyDepartureFlight(SearchFlightCriteria criteria) {
        verifyFlight(Direction.DEPARTURE, criteria.getDepartureAirport(), criteria.getArrivalAirport(), criteria.getDepartureDate());
    }

    @Step("Verify return flight information for criteria: {criteria}")
    public void verifyReturnFlight(SearchFlightCriteria criteria) {
        verifyFlight(Direction.RETURN, criteria.getArrivalAirport(), criteria.getDepartureAirport(), criteria.getReturnDate());
    }

    /**
     * Verify flight information displayed on the passenger information page.
     *
     * @param direction the flight direction (departure or return)
     * @param from      the departure airport
     * @param to        the arrival airport
     * @param date      the flight date
     */
    private void verifyFlight(Direction direction, Airport from, Airport to, LocalDate date) {
        String directionText = direction == Direction.DEPARTURE ? "departureFlight" : "returnFlight";
        ElementsCollection infos = ticketInfoHeaders.findBy(text(i18n.t("vietjet.panel." + directionText))).sibling(0).$$("h5");
        infos.get(0).shouldHave(text("%s (%s)".formatted(
                i18n.t("vietjet.airport." + from.getCode()),
                from.getCode()
        )));
        infos.get(1).shouldHave(text("%s (%s)".formatted(
                i18n.t("vietjet.airport." + to.getCode()),
                to.getCode()
        )));
        infos.get(2).shouldHave(partialText(date.format(DateTimeFormatter.ofPattern("EEE, dd/MM/yyyy", i18n.getCurrentLang()))));
    }
}
