package com.agest.page.agoda;

import com.agest.utils.DriverUtils;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    private final SelenideElement searchButton = $x("//button[@data-selenium='searchButton']");

    public SearchResultPage clickSearchButton() {
        boolean success = DriverUtils.performActionAndSwitchToNewWindow(searchButton::click, 2);

        if (success) {
            logger.info("Search completed successfully");
            return page(SearchResultPage.class);
        } else {
            throw new Error("Failed to click search button or switch to new window");
        }
    }
}
