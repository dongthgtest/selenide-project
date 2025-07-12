package com.agest.page.vietjet;

import com.agest.model.vietjet.SearchFlightCriteria;
import com.agest.page.IPage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.partialText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

public class PassengerInformationPage implements IPage {

    public void verifyPassengerInformationPageDisplayed() {
        titleLabel.shouldHave(text(i18n.t("vietjet.passengerInfo.title")));
    }

    public void verifyTicketInformation(SearchFlightCriteria criteria) {
        verifyDepartureFlight(criteria);
        if (criteria.isRoundTrip()) {
            verifyReturnFlight(criteria);
        }
    }

    public void verifyDepartureFlight(SearchFlightCriteria criteria) {
        ElementsCollection infos = ticketInfoHeaders.findBy(text(i18n.t("vietjet.panel.departureFlight"))).sibling(0).$$("h5");
        infos.get(0).shouldHave(text("%s (%s)".formatted(
                i18n.t("vietjet.airport." + criteria.getDepartureAirport().getCode()),
                criteria.getDepartureAirport().getCode()
        )));
        infos.get(1).shouldHave(text("%s (%s)".formatted(
                i18n.t("vietjet.airport." + criteria.getArrivalAirport().getCode()),
                criteria.getArrivalAirport().getCode()
        )));
        infos.get(2).shouldHave(partialText(criteria.getDepartureDate().format(DateTimeFormatter.ofPattern("EEE, dd/MM/yyyy", i18n.getCurrentLang()))));
    }

    public void verifyReturnFlight(SearchFlightCriteria criteria) {
        ElementsCollection infos = ticketInfoHeaders.findBy(text(i18n.t("vietjet.panel.returnFlight"))).sibling(0).$$("h5");
        infos.get(0).shouldHave(text("%s (%s)".formatted(
                i18n.t("vietjet.airport." + criteria.getArrivalAirport().getCode()),
                criteria.getArrivalAirport().getCode()
        )));
        infos.get(1).shouldHave(text("%s (%s)".formatted(
                i18n.t("vietjet.airport." + criteria.getDepartureAirport().getCode()),
                criteria.getDepartureAirport().getCode()
        )));
        infos.get(2).shouldHave(partialText(criteria.getReturnDate().format(DateTimeFormatter.ofPattern("EEE, dd/MM/yyyy", i18n.getCurrentLang()))));
    }

    private final SelenideElement titleLabel = $("h3.MuiTypography-h3");
    private final ElementsCollection ticketInfoHeaders = $$x("//div[p[@variantlg='h3']]");
}
