package com.agest.page.agoda;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BasePage {
    private final SelenideElement selectedLanguageContainer = $("[data-selenium='language-container-selected-language']");
    private final SelenideElement searchInput = $("#textInput");
    private final SelenideElement nextMonthButton = $("[data-selenium='calendar-next-month-button']");
    private final SelenideElement prevMonthButton = $("[data-selenium='calendar-previous-month-button']");
    private final SelenideElement todayOnCalendar = $x("//div[contains(@class,'today')]//span");
    private final SelenideElement checkInDate = $$x("//div[contains(@class,'container--selected')]//span").first();

    @Step("Select language: {language}")
    public void selectLanguage(String language) {
        selectedLanguageContainer.click();
        SelenideElement languageOption = $x("//li[@id='language-popup-section-all']//following-sibling::div//p[text()='" + language + "']");
        languageOption.should(exist);
        languageOption.click();
        selectedLanguageContainer.should(visible);
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
    public void findAndSelectCheckInDate(LocalDate targetDate) {
        LocalDate today = LocalDate.parse(Objects.requireNonNull(todayOnCalendar.getAttribute("data-selenium-date")));
        moveCalendarToCorrectMonth(today, targetDate);
        this.selectTargetDate(targetDate);
    }

    public void findAndSelectCheckOutDate(LocalDate targetDate) {
        LocalDate today = LocalDate.parse(Objects.requireNonNull(checkInDate.getAttribute("data-selenium-date")));
        moveCalendarToCorrectMonth(today, targetDate);
        this.selectTargetDate(targetDate);
    }

    @Step("Move calendar to the correct month for date: {targetDate}")
    public void moveCalendarToCorrectMonth(LocalDate today, LocalDate targetDate) {
        long numberOfMonthsDifference = ChronoUnit.MONTHS.between(today, targetDate);

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

    @Step("Select target date: {targetDate}")
    public void selectTargetDate(LocalDate targetDate) {
        $("[data-selenium-date='%s']".formatted(targetDate.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .should(exist)
                .click();
    }
}
