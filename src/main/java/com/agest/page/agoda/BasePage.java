package com.agest.page.agoda;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class BasePage {
    private final SelenideElement selectedLanguageContainer = $("[data-selenium='language-container-selected-language']");
    private final SelenideElement searchInput = $("#textInput");
    private final SelenideElement nextMonthButton = $("[data-selenium='calendar-next-month-button']");
    private final SelenideElement prevMonthButton = $("[data-selenium='calendar-previous-month-button']");

    public void selectLanguage(String language) {
        selectedLanguageContainer.click();
        SelenideElement languageOption = $x("//li[@id='language-popup-section-all']//following-sibling::div//p[text()='" + language + "']");
        languageOption.should(exist);
        languageOption.click();
        selectedLanguageContainer.should(visible);
    }

    public void searchAndSelectLocation(String location) {
        searchInput.should(exist);
        searchInput.click();
        searchInput.setValue(location);
        this.chooseFirstSuggestion(location);
    }

    public void chooseFirstSuggestion(String location) {
        var topSuggestion = $("[data-selenium='autosuggest-item'][data-element-index='0']");
        topSuggestion.shouldHave(Condition.text(location));
        topSuggestion.click();
    }

    public void findAndSelectDateFromCalendar(LocalDate targetDate) {
        long numberOfMonthsDifference = ChronoUnit.MONTHS.between(LocalDate.now(), targetDate);

        if (numberOfMonthsDifference > 0) {
            for (int i = 0; i < numberOfMonthsDifference; i++) {
                nextMonthButton.click();
            }
        } else if (numberOfMonthsDifference < 0) {
            for (int i = 0; i < Math.abs(numberOfMonthsDifference); i++) {
                prevMonthButton.click();
            }
        }
        this.selectTargetDate(targetDate);
    }

    public void selectTargetDate(LocalDate targetDate) {
        $("[data-selenium-date='" + targetDate.toString() + "']").should(exist).click();
    }
}
