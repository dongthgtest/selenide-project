package com.agest.page.vietjet;

import com.agest.constant.vietjet.Airport;
import com.agest.page.IPage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.url;

@Slf4j
public class SelectTravelOptionPage extends BasePage implements IPage {
    private final SelenideElement closeDialogButton = $("[aria-label=close]");
    private final ElementsCollection ticketPriceLabels = $$(".MuiGrid-item p.MuiTypography-h4.MuiTypography-root:not([variantlg])");
    private final ElementsCollection ticketSuffixPriceLabels = $$(".MuiGrid-item .MuiTypography-body1");
    private final ElementsCollection headerLabels = $$("p[variantmd=h3]");
    protected final ElementsCollection selectedFlightDate = $$(".slick-current p");

    @Step("Verify Select Travel Options page is displayed")
    public void verifyPageIsDisplayed() {
        // Implementation to verify the page is displayed
        Assert.assertTrue(url().endsWith("/select-flight"), "Select Travel Options page is not displayed");
    }

    @Step("Close dialog if needed")
    public void closeDialog() {
        // Implementation to close the dialog
        if (closeDialogButton.is(visible, Duration.ofSeconds(10))) {
            closeDialogButton.click();
        }
    }

    @Step("Select the cheapest ticket")
    public void selectCheapestTicket() {
        // Implementation to select the cheapest ticket
        int cheapestPrice = getCheapestPrice();
        selectTicketByPrice(cheapestPrice);
    }

    @Step("Click on Next button")
    public void goToNext() {
        getRippleButton(i18n.t("vietjet.selectFlight.nextButton")).click();
    }

    @Step("Verify the ticket price is displayed in VND")
    public void verifyTicketPriceDisplayedInVND() {
        ticketSuffixPriceLabels.shouldHave(sizeGreaterThan(0), Duration.ofSeconds(10));
        ticketSuffixPriceLabels.shouldHave(allMatch(
                "All ticket prices should be displayed in VND",
                label -> label.getText().endsWith("VND")
        ));
    }

    @Step("Verify the flights dates are displayed correctly")
    public void verifyFlightDate(LocalDate expectedDate) {
        // Implementation to verify flight date
        selectedFlightDate.get(0).shouldHave(text(expectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, i18n.getCurrentLang())));
        Assert.assertEquals(getSelectedDate(), MonthDay.from(expectedDate), "Flight date does not match expected date");
    }

    @Step("Verify The departure and arrival places are correct")
    public void verifyDepartureAndArrivalPlaces(Airport from, Airport to) {
        // Departure flight
        headerLabels.get(1).$("span", 0).shouldHave(text(i18n.t("vietjet.home.departureLabel")));
        headerLabels.get(1).$("span", 1).shouldHave(text("%s ( %s )".formatted(
                i18n.t("vietjet.airport.%s".formatted(from.getCode())),
                from.getCode()
        )));

        // Arrival flight
        headerLabels.get(2).$("span", 0).shouldHave(text(i18n.t("vietjet.home.arrivalLabel")));
        headerLabels.get(2).$("span", 1).shouldHave(text("%s ( %s )".formatted(
                i18n.t("vietjet.airport.%s".formatted(to.getCode())),
                to.getCode()
        )));
    }

    @Step("Verify number of passenger is correct")
    public void verifyPassengerCount(int adultCount, int childCount, int infantCount) {
        List<String> passengerLabels = new ArrayList<>();
        if (adultCount > 0) passengerLabels.add(adultCount + " " + i18n.t("vietjet.common.adultLabel"));
        if (childCount > 0) passengerLabels.add(childCount + " " + i18n.t("vietjet.common.childLabel"));
        if (infantCount > 0) passengerLabels.add(infantCount + " " + i18n.t("vietjet.common.infantLabel"));
        headerLabels.first().$("span", 1).shouldHave(text(String.join(", ", passengerLabels)));
    }

    /**
     * Selects a ticket by its price.
     *
     * @param price The price of the ticket to select.
     */
    private void selectTicketByPrice(int price) {
        // Implementation to select a ticket by price
        String formattedPrice = String.format("%,d", price);
        Allure.step("Select ticket with price: " + formattedPrice);
        ticketPriceLabels.findBy(text(formattedPrice)).scrollIntoView(true).click();
    }

    /**
     * Gets the selected flight date from the page.
     *
     * @return The selected flight date as a MonthDay object.
     */
    private MonthDay getSelectedDate() {
        String raw = selectedFlightDate.get(1).getText().replaceAll("(st|nd|rd|th)$", ""); // Remove ordinal suffixes if present
        return MonthDay.parse(raw, DateTimeFormatter.ofPattern(i18n.t("vietjet.selectFlight.stickyHeader.monthDayFormat"), i18n.getCurrentLang()));
    }

    /**
     * Retrieves the cheapest ticket price from the page.
     *
     * @return The cheapest ticket price as an integer.
     */
    private int getCheapestPrice() {
        // This page is lazy loaded, so we need to ensure the ticket prices are loaded
        int total = -1; // Initialize total to an invalid value
        while (total != ticketPriceLabels.size()) {
            total = ticketPriceLabels.size();
            ticketPriceLabels.last().scrollIntoView(true);
            Selenide.sleep(Duration.ofSeconds(1).toMillis()); // Wait for the prices to load
        }
        return ticketPriceLabels.stream()
                .mapToInt(label -> Integer.parseInt(label.getText().replaceAll(",", "")))
                .min()
                .orElseThrow(() -> new IllegalStateException("No ticket prices found"));
    }
}
