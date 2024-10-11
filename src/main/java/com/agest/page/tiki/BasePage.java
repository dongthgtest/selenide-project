package com.agest.page.tiki;

import com.agest.utils.Actions;
import com.agest.utils.Constants;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class BasePage {
    public String getBreadcrumb() {
        List<SelenideElement> breadcrumbs = $$x("//div[@class='breadcrumb']//a");
        List<String> breadcrumbsList = new ArrayList<>();
        breadcrumbs.forEach(b -> {
            breadcrumbsList.add(b.getText());
        });
        return breadcrumbsList.size() > 1
                ? String.join(" > ", breadcrumbsList)
                : breadcrumbsList.toString();
    }

    public void goToHomePage() {
        $x("//a[text()='Trang chủ']").click();
    }

    public void closeAdsIfDisplayed() {
        SelenideElement closeAdsButton = $x("//div[@id='VIP_BUNDLE']//picture");
        if (Actions.isElementDisplayed(closeAdsButton, Constants.DISPLAY_TIMEOUT)) {
            closeAdsButton.click();
            closeAdsButton.shouldNot(visible);
        }
    }

    public void waitForPageLoad() {
        Selenide.sleep(3000);
    }
}
