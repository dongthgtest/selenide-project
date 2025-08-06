package com.agest.page.vietjet;

import com.agest.constant.vietjet.Airport;
import com.agest.constant.vietjet.TicketType;
import com.agest.model.vietjet.SearchFlightCriteria;
import com.agest.page.IPage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Slf4j
public class HomePage extends BasePage implements IPage {
    private final SelenideElement popUpDialog = $("#popup-dialog-description");
    private final SelenideElement acceptDialog = $x("//div[@role='dialog']//span/h5");
    private final SelenideElement returnRadioButton = $("[value=roundTrip]");
    private final SelenideElement oneWayRadioButton = $("[value=oneway]");
    private final ElementsCollection calendarHeaderLabel = $$(".rdrMonthName");
    private final SelenideElement calendarPrevButton = $(".rdrPprevButton");
    private final SelenideElement calendarNextButton = $(".rdrNextButton");

    @Step("Accept cookie dialog")
    public void acceptCookie() {
        popUpDialog.should(exist);
        acceptDialog.click();
    }

    @Step("Search flight with criteria: {criteria}")
    public void searchFlight(SearchFlightCriteria criteria) {
        selectTicketType(criteria.getTicketType());
        selectDepartureAirport(criteria.getDepartureAirport());
        selectReturnAirport(criteria.getArrivalAirport());
        selectDate(criteria.getDepartureDate());
        if (criteria.isRoundTrip()) {
            selectDate(criteria.getReturnDate());
        }
        selectAdultCount(criteria.getNumberOfAdults());
        selectChildCount(criteria.getNumberOfChildren());
        selectInfantCount(criteria.getNumberOfInfants());
        if (criteria.isFindCheapest()) {
            checkFindLowFare();
        }
        clickFindFlightButton();
    }

    @Step("Check 'Find low fare' checkbox")
    public void checkFindLowFare() {
        SelenideElement searchCheapestTicketCheckbox = getSearchCheapestTicketCheckbox();
        if (!searchCheapestTicketCheckbox.isSelected()) {
            searchCheapestTicketCheckbox.click();
        }
    }

    @Step("Select ticket type: {ticketType}")
    protected void selectTicketType(TicketType ticketType) {
        if (ticketType == TicketType.ONE_WAY) {
            if (!oneWayRadioButton.isSelected()) {
                oneWayRadioButton
                        .should(visible)
                        .setSelected(true);
            }
        } else if (ticketType == TicketType.ROUND_TRIP) {
            if (!returnRadioButton.isSelected()) {
                returnRadioButton
                        .should(visible)
                        .setSelected(true);
            }
        }
    }

    @Step("Select departure airport: {airport}")
    protected void selectDepartureAirport(Airport airport) {
        SelenideElement departureInput = getDepartureAirportInput();
        if (!departureInput.is(focused)) {
            departureInput.click();
        }
        departureInput.type(airport.getName());
        getDestinationOption(airport).click();
    }

    @Step("Select return airport: {airport}")
    protected void selectReturnAirport(Airport airport) {
        SelenideElement returnInput = getReturnAirportInput();
        if (!returnInput.is(focused)) {
            returnInput.click();
        }
        returnInput.type(airport.getName());
        getDestinationOption(airport).click();
    }

    @Step("Select number of adults: {count}")
    private void selectAdultCount(int count) {
        selectPassengerCount(i18n.t("vietjet.common.adultLabel"), count);
        log.debug("Selected {} adults", count);
    }

    @Step("Select number of children: {count}")
    private void selectChildCount(int count) {
        selectPassengerCount(i18n.t("vietjet.common.childLabel"), count);
        log.debug("Selected {} children", count);
    }

    @Step("Select number of infants: {count}")
    private void selectInfantCount(int count) {
        selectPassengerCount(i18n.t("vietjet.common.infantLabel"), count);
        log.debug("Selected {} infants", count);
    }

    private void selectPassengerCount(String label, int count) {
        SelenideElement passengerContainer = getPassengerContainer(label);
        int current = getNumberOfPassengers(label);
        int diff = count - current;
        log.debug("Current count: {}, Desired count: {}, Difference: {}", current, count, diff);
        if (diff > 0) {
            for (int i = 0; i < diff; i++) {
                passengerContainer.$$("button").last().click();
            }
        } else if (diff < 0) {
            for (int i = 0; i < -diff; i++) {
                passengerContainer.$$("button").first().click();
            }
        }
    }

    private SelenideElement getDepartureAirportInput() {
        return getAirportInput(i18n.t("vietjet.home.departureLabel"));
    }

    private SelenideElement getReturnAirportInput() {
        return getAirportInput(i18n.t("vietjet.home.arrivalLabel"));
    }

    private SelenideElement getAirportInput(String label) {
        return $x(String.format("//label[text()='%s']/following-sibling::div/input", label));
    }

    @Step("Select departure date: {date}")
    /**
     * Selects a departure date in the calendar. This method works both for one-way and round-trip tickets.
     *
     * @param date The date to select.
     */
    private void selectDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(i18n.t("vietjet.common.calendarHeaderFormat"));
        calendarHeaderLabel.shouldHave(size(2));
        YearMonth leftMonth = YearMonth.parse(calendarHeaderLabel.first().getText(), formatter);
        YearMonth rightMonth = YearMonth.parse(calendarHeaderLabel.last().getText(), formatter);
        YearMonth target = YearMonth.from(date);
        log.debug("Current month: {}, Target month: {}", leftMonth, target);
        if (target.isBefore(leftMonth)) {
            do {
                log.debug("Navigating to previous month: {}", leftMonth);
                calendarPrevButton.click();
                leftMonth = YearMonth.parse(calendarHeaderLabel.first().getText(), formatter);
            } while (target.isBefore(leftMonth));
        } else if (target.isAfter(rightMonth)) {
            do {
                log.debug("Navigating to next month: {}", rightMonth);
                calendarNextButton.click();
                rightMonth = YearMonth.parse(calendarHeaderLabel.last().getText(), formatter);
            } while (target.isAfter(rightMonth));
        }
        SelenideElement dateElement = getCalendarDate(date);
        dateElement.should(exist);
        dateElement.click();
    }

    @Step("Select calendar date: {date}")
    /**
     * Selects a date in the calendar. Make sure the date you want to select is visible in the calendar.
     */
    private SelenideElement getCalendarDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(i18n.t("vietjet.common.calendarHeaderFormat"));
        String calendarHeader = date.format(formatter);
        return calendarHeaderLabel.findBy(text(calendarHeader))
                .sibling(1)
                .$x(".//span[@class='rdrDayNumber']/span[text()='%d']".formatted(date.getDayOfMonth()));
    }

    /**
     * Selects the destination airport in the dropdown.
     *
     * @param airport The airport to select.
     */
    private SelenideElement getDestinationOption(Airport airport) {
        return $x(String.format("//div[@id='panel1a-content']//div[contains(@class, 'MuiBox-root') and div/div[@translate='no' and text()='%s']]", airport.getCode()));
    }

    private int getNumberOfPassengers(String label) {
        return Integer.parseInt(getPassengerContainer(label).$x(".//span[contains(@class, 'MuiTypography-colorPrimary')]").getText());
    }

    private SelenideElement getPassengerContainer(String label) {
        return $x(String.format("//div[div[p[text()='%s']]]/following-sibling::div", label));
    }

    private SelenideElement getFindFlightButton() {
        // there are two buttons with the same text, so we need to specify the one that is clickable
        ElementsCollection buttons = $$x("//button[descendant::span[text()=\"%s\"]]".formatted(i18n.t("vietjet.home.findFlightButton")));
        if (buttons.last().is(visible)) {
            return buttons.last();
        }
        return buttons.first();
    }

    private SelenideElement getSearchCheapestTicketCheckbox() {
        ElementsCollection checkboxes = $$x("//h3[text()='%s']/preceding-sibling::span//input[@type='checkbox']".formatted(i18n.t("vietjet.home.findLowestFareLabel")));
        return checkboxes.last();
    }

    @Step("Click 'Find Flight' button")
    private void clickFindFlightButton() {
        SelenideElement findFlightButton = getFindFlightButton();
        findFlightButton.should(clickable);
        findFlightButton.click();
    }
}
