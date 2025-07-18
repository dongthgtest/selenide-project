package com.agest.page.vietjet;

import com.agest.constant.vietjet.Direction;
import com.agest.page.IPage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.YearMonth;

import static com.agest.constant.Formatter.YEAR_MONTH_FORMATTER;
import static com.agest.constant.vietjet.Direction.DEPARTURE;
import static com.agest.constant.vietjet.Direction.RETURN;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.WebDriverRunner.url;

@Slf4j
public class SelectFlightCheapPage extends SelectTravelOptionPage implements IPage {
    private final ElementsCollection flightHeaderContainers = $$x("//div[p[contains(@class, 'MuiTypography-h2')]]");

    @Step("Verify Select Flight Cheap page is displayed")
    public void verifyPageIsDisplayed() {
        // Implementation to verify the page is displayed
        Assert.assertTrue(url().endsWith("/select-flight-cheap"), "Select Flight Cheap page is not displayed");
    }

    /**
     * Find the cheapest flight for a given direction and day of month within next 3 months.
     *
     * @param daysTrip
     * @return
     */
    public Pair<LocalDate, LocalDate> findCheapestReturnFlight(
            int daysTrip
    ) {
        int minPrice = Integer.MAX_VALUE;
        Pair<LocalDate, LocalDate> cheapestFlight = null;
        YearMonth currentDepartureMonth = getSelectedDepartureMonth();
        for (int i = 0; i < 3; i++) {

            LocalDate startDate = LocalDate.of(currentDepartureMonth.getYear(), currentDepartureMonth.getMonth(), getEarliestAvailableFlightDate());
            LocalDate lastDayOfMonth = currentDepartureMonth.atEndOfMonth();

            int firstAvailableDay = startDate.getDayOfMonth();
            int lastAvailableDay = lastDayOfMonth.getDayOfMonth();

            for (int day = firstAvailableDay; day <= lastAvailableDay; day++) {
                startDate = LocalDate.of(currentDepartureMonth.getYear(), currentDepartureMonth.getMonth(), day);

                LocalDate endDate = startDate.plusDays(daysTrip);

                int departurePrice = getPriceByDate(DEPARTURE, startDate);
                log.debug("Departure flight price on {}: {}", startDate, departurePrice);

                int returnPrice = getPriceByDate(RETURN, endDate);
                log.debug("Return flight price on {}: {}", endDate, returnPrice);

                int totalPrice = departurePrice + returnPrice;
                log.info("Total price for departure on {} and return on {}: {}", startDate, endDate, totalPrice);

                if (totalPrice < minPrice) {
                    minPrice = totalPrice;
                    log.info("Found cheaper flight: {} VND", minPrice);
                    cheapestFlight = Pair.of(startDate, endDate);
                }
            }

            // Move to the next month
            currentDepartureMonth = currentDepartureMonth.plusMonths(1);
        }

        return cheapestFlight;
    }

    @Step("Select departure flight on {date}")
    public void selectDepartureFlight(LocalDate date) {
        log.info("Selecting departure flight on {}", date);
        selectMonth(DEPARTURE, YearMonth.from(date));
        SelenideElement departureFlight = getDepartureFlightHeaderContainer()
                .sibling(1)
                .$$x(".//div[@role='button' and div/span]/p")
                .findBy(text(String.valueOf(date.getDayOfMonth())));
        departureFlight.scrollIntoCenter().click();
    }

    @Step("Select return flight on {date}")
    public void selectReturnFlight(LocalDate date) {
        log.info("Selecting return flight on {}", date);
        selectMonth(RETURN, YearMonth.from(date));
        SelenideElement returnFlight = getReturnFlightHeaderContainer()
                .sibling(1)
                .$$x(".//div[@role='button' and div/span]/p")
                .findBy(text(String.valueOf(date.getDayOfMonth())));
        returnFlight.scrollIntoCenter().click();
    }

    @Step("Select month {month} for flight direction {flightDirection}")
    public void selectMonth(Direction flightDirection, YearMonth month) {
        SelenideElement monthSelector = (flightDirection == DEPARTURE ? getDepartureFlightHeaderContainer() : getReturnFlightHeaderContainer())
                .sibling(0)
                .$$x(".//div[@class='slick-track']/div[contains(@class, 'slick-slide')]")
                .findBy(text(month.format(YEAR_MONTH_FORMATTER)));
        if (monthSelector.getAttribute("class").contains("slick-current")) {
            log.info("Month {} is already selected for {}", month, flightDirection.getName());
            return;
        }
        log.info("Selecting month {} for {}", month, flightDirection.getName());
        monthSelector
                .scrollIntoCenter()
                .click();
    }

    /**
     * Get the price for a flight on a specific date.
     *
     * @param direction the flight direction (departure or return)
     * @param date      the date to check
     * @return the minimum price for the flight on that date
     */
    private int getPriceByDate(Direction direction, LocalDate date) {
        YearMonth yearMonth = YearMonth.from(date);
        int dayOfMonth = date.getDayOfMonth();
        log.info("Getting minimum price for {} flight on day {}", direction.getName(), date);
        // navigate to the correct month if not already selected
        if (direction == DEPARTURE) {
            if (!getSelectedDepartureMonth().equals(yearMonth)) {
                selectMonth(DEPARTURE, yearMonth);
            }
        } else {
            if (!getSelectedReturnMonth().equals(yearMonth)) {
                selectMonth(RETURN, yearMonth);
            }
        }
        SelenideElement priceLabel = (direction == DEPARTURE ? getDepartureFlightHeaderContainer() : getReturnFlightHeaderContainer())
                .sibling(1)
                .$x(".//div[@role='button' and p[text()=%d]]//span[1]".formatted(dayOfMonth));
        return Integer.parseInt(priceLabel.getText().replaceAll(",", ""));
    }

    /**
     * Get the currently selected departure flight dates
     *
     * @return A YearMonth object representing the selected departure month.
     */
    private YearMonth getSelectedDepartureMonth() {
        String monthText = selectedFlightDate.get(0).getText();
        return YearMonth.parse(monthText, YEAR_MONTH_FORMATTER);
    }

    /**
     * Get the currently selected return flight dates
     *
     * @return A YearMonth object representing the selected return month.
     */
    private YearMonth getSelectedReturnMonth() {
        String monthText = selectedFlightDate.get(2).getText();
        return YearMonth.parse(monthText, YEAR_MONTH_FORMATTER);
    }

    private SelenideElement getDepartureFlightHeaderContainer() {
        return flightHeaderContainers.findBy(text(i18n.t("vietjet.panel.departureFlight")));
    }

    private SelenideElement getReturnFlightHeaderContainer() {
        return flightHeaderContainers.findBy(text(i18n.t("vietjet.panel.returnFlight")));
    }

    private int getEarliestAvailableFlightDate() {
        SelenideElement earliestDate = getDepartureFlightHeaderContainer()
                .sibling(1)
                .$x(".//div[@role='button' and div/span]/p");
        return Integer.parseInt(earliestDate.getText());
    }
}
