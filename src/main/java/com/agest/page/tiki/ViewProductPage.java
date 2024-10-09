package com.agest.page.tiki;

import com.agest.model.tiki.LeftMenuItem;
import com.agest.utils.Constants;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

public class ViewProductPage extends BasePage {
    private final SelenideElement viewRecentlyItem = $x("//div" +
            "[@data-view-id='product_list_recently_view_container']");

    @Step("Verify breadcrumb display correctly")
    public void shouldViewProductPageDisplayed(LeftMenuItem item) {
        String dynamicBreadcrumb = "//div[@class='breadcrumb']//span[@title='%s']";
        $x(String.format(dynamicBreadcrumb, item.getTitle())).should(visible, Constants.SHORT_WAIT);
    }

    @Step("Select first product")
    public void selectFirstProduct() {
        SelenideElement firstProduct = $x("//div//a[@data-view-id='product_list_item']");
        firstProduct.scrollTo();
        firstProduct.click();
    }

    @Step("Verify viewed item is display in view recently area")
    public void shouldViewedItemDisplayInViewRecentlySection(String itemName) {
        viewRecentlyItem.scrollTo();
        SelenideElement productViewRecently = $x("//div[@data-view-id='product_list_recently_view_container']" +
                "//div//h3[text()='" + itemName + "']");
        productViewRecently.should(visible);
    }

    @Step("Click filter all")
    public void clickFilterAll() {
        SelenideElement filterAll = $x("//button[img[@alt='filters']]");
        filterAll.scrollTo();
        filterAll.click();
    }

    @Step("Verify all filters modal is displayed")
    public void shouldFilterAllDialogDisplayed() {
        $x("//div[contains(@class,'Modal')]//div[@class='title']").should(visible, Constants.DISPLAY_TIMEOUT);
    }

    @Step("Get the price of all displayed items")
    public void shouldPriceOfAllDisplayedItemsWithinRange(String startPrice, String endPrice) {
        int start = Integer.parseInt(startPrice);
        int end = Integer.parseInt(endPrice);
        List<SelenideElement> displayedItems = $$(".price-discount__price");
        List<Integer> outOfRangePrices = new ArrayList<>();

        displayedItems.forEach(item -> {
            item.shouldBe(visible);
            item.shouldBe(enabled);
            item.scrollTo();
            int itemPrice = Integer.parseInt(item.getText()
                    .substring(0, item.getText().length() - 1)
                    .replace(".", "")
                    .trim());
            if (itemPrice < start || itemPrice > end) {
                outOfRangePrices.add(itemPrice);
            }
        });
        Assert.assertTrue(outOfRangePrices.isEmpty(),
                "Item prices out of range: " + outOfRangePrices);
    }
}
