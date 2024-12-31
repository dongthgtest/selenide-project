package com.agest.page.vietjet;

import com.agest.model.vietjet.DepartureLocation;
import com.agest.utils.LanguageUtils;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class HomePage {
    private final LanguageUtils language;
    private final SelenideElement popUpDialog = $("#popup-dialog-description");
    private final SelenideElement acceptDialog = $x("//div[@role='dialog']//span/h5");
    private final String dynamicInput = "//label[contains(@class,'MuiInput') and text()='%s']";
    private final String dynamicLocation = "//div[@id='panel1a-content']//div[text()='%s']";

    public HomePage(String languageCode) {
        this.language = new LanguageUtils(languageCode);
    }

    public void acceptCookie() {
        popUpDialog.should(exist);
        acceptDialog.click();
    }

    public void departureFrom(DepartureLocation location) {
        SelenideElement fromDropdown = $x(String.format(dynamicInput, language.getMessage("departureInput")));
        SelenideElement locationOption = $x(String.format(dynamicLocation, location.getName()));
        fromDropdown.click();
        locationOption.should(exist);
        locationOption.click();
    }

    public void returnTo(DepartureLocation location) {
        SelenideElement returnDropdown = $x(String.format(dynamicInput, language.getMessage("arrivalInput")));
        SelenideElement locationOption = $x(String.format(dynamicLocation, location.getName()));
        returnDropdown.click();
        locationOption.should(exist);
        locationOption.click();
    }
}
