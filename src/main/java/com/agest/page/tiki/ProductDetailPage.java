package com.agest.page.tiki;

import com.agest.utils.Constants;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProductDetailPage extends BasePage {
    public void shouldDisplayed() {
        $(".product-price__current-price").should(visible, Constants.DISPLAY_TIMEOUT);
    }

    public String getItemName() {
        return $x("//h1[contains(@class,'Title')]").getText();
    }
}
