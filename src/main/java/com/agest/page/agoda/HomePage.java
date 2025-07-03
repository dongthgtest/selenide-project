package com.agest.page.agoda;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage {
    private final SelenideElement searchButton = $x("//button[@data-selenium='searchButton']");

    public SearchResultPage clickSearchButton() {
        searchButton.shouldBe(visible).click();
        switchTo().window(1);
        return page(SearchResultPage.class);
    }
}
