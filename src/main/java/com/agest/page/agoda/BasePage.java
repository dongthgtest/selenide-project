package com.agest.page.agoda;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BasePage {
    private final SelenideElement selectedDay = $$x("//div[@aria-selected='true']//span").first();
    private final SelenideElement selectedLanguageContainer = $("[data-selenium='language-container-selected-language']");
    private final SelenideElement searchInput = $("#textInput");
    private final SelenideElement nextMonthButton = $("[data-selenium='calendar-next-month-button']");
    private final SelenideElement prevMonthButton = $("[data-selenium='calendar-previous-month-button']");
    private final String dynamicButton = "//button[@data-element-name='%s' and @data-selenium='%s']";
    private final String dynamicCurrentQuantity = "//div[contains(@data-selenium,'%s')]/p";

    @Step("Select language: {language}")
    public void selectLanguage(String language) {
        selectedLanguageContainer.click();
        SelenideElement languageOption = $x("//li[@id='language-popup-section-all']//following-sibling::div//p[text()='" + language + "']");
        languageOption.should(exist);
        languageOption.click();
        selectedLanguageContainer.should(visible);
    }

    @Step("Search hotel by location - {location}, check-in date - {checkInDate}, check-out date - {checkOutDate}, " +
            "rooms - {roomCount}, adults - {adultCount}")
    public void searchHotel(String location, LocalDate checkInDate, LocalDate checkOutDate, int roomCount, int adultCount) {
        this.searchAndSelectLocation(location);
        this.findAndSelectDate(checkInDate);
        this.findAndSelectDate(checkOutDate);
        this.selectRoomQuantity(roomCount);
        this.selectAdultQuantity(adultCount);
    }

    @Step("Search and select location: {location}")
    public void searchAndSelectLocation(String location) {
        searchInput.should(exist);
        searchInput.click();
        searchInput.setValue(location);
        this.chooseFirstSuggestion(location);
    }

    @Step("Choose the first suggestion for location: {location}")
    public void chooseFirstSuggestion(String location) {
        var topSuggestion = $("[data-selenium='autosuggest-item'][data-element-index='0']");
        topSuggestion.shouldHave(Condition.text(location));
        topSuggestion.click();
    }

    @Step("Find and select date from calendar: {targetDate}")
    public void findAndSelectDate(LocalDate targetDate) {
        LocalDate selectedDay = LocalDate.parse(Objects.requireNonNull(this.selectedDay.getAttribute("data-selenium-date")));
        moveCalendarToCorrectMonth(selectedDay, targetDate);
        this.selectTargetDate(targetDate);
    }

    @Step("Move calendar to the correct month for date: {targetDate}")
    public void moveCalendarToCorrectMonth(LocalDate today, LocalDate targetDate) {
        long numberOfMonthsDifference = (targetDate.getYear() - today.getYear()) * 12L
                + (targetDate.getMonthValue() - today.getMonthValue());

        if (numberOfMonthsDifference > 1) {
            for (int i = 0; i < numberOfMonthsDifference; i++) {
                nextMonthButton.click();
            }
        } else if (numberOfMonthsDifference < 0) {
            for (int i = 0; i < Math.abs(numberOfMonthsDifference); i++) {
                prevMonthButton.click();
            }
        }
    }

    @Step("Select room quantity: {roomCount}")
    public void selectRoomQuantity(int roomCount) {
        this.selectQuantity(roomCount, "occupancy-selector-panel-rooms", "occ-room-value");
    }

    @Step("Select adult quantity: {adultCount}")
    public void selectAdultQuantity(int adultCount) {
        this.selectQuantity(adultCount, "occupancy-selector-panel-adult", "occ-adult-value");
    }

    private void selectQuantity(int roomCount, String option, String currentOption) {
        SelenideElement minusButton = $x(String.format(dynamicButton, option, "minus"));
        SelenideElement plusButton = $x(String.format(dynamicButton, option, "plus"));
        SelenideElement currentQuantityLabel = $x(String.format(dynamicCurrentQuantity, currentOption));
        int numberDifference = roomCount - Integer.parseInt(currentQuantityLabel.getText());
        if (numberDifference > 0) {
            for (int i = 0; i < numberDifference; i++) {
                plusButton.click();
            }
        } else if (numberDifference < 0) {
            for (int i = 0; i < Math.abs(numberDifference); i++) {
                minusButton.click();
            }
        }
    }

    @Step("Select target date: {targetDate}")
    public void selectTargetDate(LocalDate targetDate) {
        $("[data-selenium-date='%s']".formatted(targetDate.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .should(exist)
                .click();
    }
}
