package com.agest.page.tiki;

import com.agest.model.tiki.LeftMenuItem;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class HomePage extends BasePage {
    public void selectLeftMenuItem(LeftMenuItem item) {
        String dynamicItem = "//div[text()='Danh mục']/following-sibling::div//div[@title='%s']";
        $x(String.format(dynamicItem, item.getTitle())).scrollTo().click();
    }

    public void shouldDisplayed() {
        $x("//div[text()='Danh mục']").should(visible);
    }
}
