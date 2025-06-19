package com.agest.page.agoda;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class BasePage {
    private final SelenideElement selectedLanguageContainer = $("[data-selenium='language-container-selected-language']");
    private final SelenideElement searchInput = $("#textInput");

    public void selectLanguage(String language) {
        selectedLanguageContainer.click();
        SelenideElement languageOption = $("//div[@data-value='" + language + "']//p[text()='English']");
        languageOption.should(exist);
        languageOption.click();
        selectedLanguageContainer.should(visible);
    }

    public void searchForLocation(String location) {
        searchInput.should(exist);
        searchInput.click();
        searchInput.setValue(location);
        var topSuggestion = $("[data-selenium='autosuggest-item'][data-element-index='0']");
        topSuggestion.shouldHave(Condition.text(location));
        topSuggestion.click();
    }
}
