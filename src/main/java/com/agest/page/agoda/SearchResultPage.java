package com.agest.page.agoda;

import com.agest.model.agoda.FilterHotelCriteria;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SearchResultPage {
    private final SelenideElement sortLowestPriceButton = $x("//button[@data-element-name='search-sort-price']");
    private final SelenideElement minPriceTextbox = $("#price_box_0");
    private final SelenideElement maxPriceTextbox = $("#price_box_1");

    public void shouldSearchResultDisplayed(int expectedHotelsFound, String destination) {
        ElementsCollection hotelList = getHotelList();
        hotelList.shouldHave(sizeGreaterThan(expectedHotelsFound));
        for (int i = 0; i < expectedHotelsFound; i++) {
            SelenideElement hotel = getHotelList().get(i); // re-fetch each time
            hotel.$("div[data-selenium='area-city'] span")
                    .scrollIntoView("{block: 'center'}")
                    .shouldBe(visible)
                    .shouldHave(text(destination));
        }
    }

    public ElementsCollection getHotelList() {
        return $$x("//li[@data-selenium='hotel-item']");
    }

    public void sortByLowestPrice() {
        sortLowestPriceButton.shouldBe(visible).scrollIntoCenter().click();
    }

    public void shouldSortByLowestPrice(int expectedHotels) {
        sortLowestPriceButton.shouldHave(attribute("aria-current", "true"));
        List<Integer> prices = new ArrayList<>();
        ElementsCollection hotelList = getHotelList();
        int index = 0;
        while (prices.size() < expectedHotels && index < hotelList.size()) {
            SelenideElement hotel = hotelList.get(index);
            hotel.scrollIntoView("{block: 'center'}");
            Optional<Integer> price = getHotelPriceIfPresent(hotel);
            price.ifPresent(prices::add);
            index++;
        }
        if (prices.size() < expectedHotels) {
            throw new AssertionError("Could not find " + expectedHotels + " hotel prices. Only found: " + prices.size() + " prices: " + prices);
        }
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i + 1)) {
                throw new AssertionError("Hotels are not sorted by lowest price: " + prices);
            }
        }
    }

    private Optional<Integer> getHotelPriceIfPresent(SelenideElement hotel) {
        SelenideElement hotelPrice = hotel.$("span[data-selenium='display-price']");
        if (hotelPrice.exists()) {
            hotelPrice.shouldBe(visible);
            String priceText = hotelPrice.getText();
            String price = priceText.replaceAll("\\D", "");
            if (!price.isEmpty()) {
                return Optional.of(Integer.parseInt(price));
            }
        }
        return Optional.empty();
    }

    public void applyFilter(FilterHotelCriteria criteria) {
        if (criteria.getPriceRange() != null) {
            setPriceFilter(minPriceTextbox, criteria.getPriceRange().getLeft());
            setPriceFilter(maxPriceTextbox, criteria.getPriceRange().getRight());
        }
        if (criteria.getRating() != null) {
            String dynamicRatingSelector = String.format("//label[@data-component='search-filter-starratingwithluxury']" +
                    "//div[span[div[div[span[contains(text(),'%s-')]]]]]", criteria.getRating());
            SelenideElement ratingFilter = $x(dynamicRatingSelector);
            ratingFilter.click();
        }
    }

    @Step("Set minimum price to {price}")
    protected void setPriceFilter(SelenideElement element, int price) {
        element.clear();
        element.setValue(String.valueOf(price));
    }


}
