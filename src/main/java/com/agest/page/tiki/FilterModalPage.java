package com.agest.page.tiki;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class FilterModalPage {
    @Step("Select check box of {filterType} and {filterOption}")
    public void selectFilterCheckBox(String filterType, String filterOption) {
        String dynamicFilterComboBox = "//h4[text()='%s']/following-sibling::div//div[span[text()='%s']]";
        SelenideElement filterCheckBox = $x(String.format(dynamicFilterComboBox, filterType, filterOption));
        filterCheckBox.scrollTo();
        filterCheckBox.click();
    }

    @Step("Enter price range")
    public void enterPriceRange(String startPrice, String endPrice) {
        enterStartPrice(startPrice);
        enterEndPrice(endPrice);
    }

    private void enterStartPrice(String price) {
        SelenideElement inputPrice = $x("//input[@placeholder='Từ']");
        inputPrice.scrollTo();
        inputPrice.setValue(price);
    }

    private void enterEndPrice(String price) {
        SelenideElement inputPrice = $x("//input[@placeholder='Đến']");
        inputPrice.scrollTo();
        inputPrice.setValue(price);
    }

    @Step("Click Xem kết quả")
    public void clickFindResult() {
        $x("//div[text()='Xem kết quả']").click();
    }
}
