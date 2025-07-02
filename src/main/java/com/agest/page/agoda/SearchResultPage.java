package com.agest.page.agoda;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class SearchResultPage {
    private final SelenideElement sortLowestPriceButton = $x("//button[@data-element-name='search-sort-price']");

    public void shouldSearchResultDisplayed(int expectedHotelsFound, String destination) {
        ElementsCollection hotelList = getHotelList();
        hotelList.shouldHave(sizeGreaterThan(expectedHotelsFound));
        List<SelenideElement> firstFiveHotel = hotelList.stream().limit(5).toList();
        firstFiveHotel.forEach(hotel -> hotel
                .$("div[data-selenium='area-city'] span")
                .scrollIntoView("{block: 'center'}")
                .shouldBe(visible)
                .shouldHave(text(destination)));
    }

    public ElementsCollection getHotelList() {
        return $$x("//li[@data-selenium='hotel-item']");
    }

    public void sortByLowestPrice() {
        sortLowestPriceButton.shouldBe(visible).scrollIntoCenter().click();
    }

    public void shouldSortByLowestPrice() {
        sortLowestPriceButton.shouldHave(attribute("aria-current", "true"));
        ElementsCollection hotelList = getHotelList();

        List<Integer> prices = new ArrayList<>();

        for (SelenideElement hotel : hotelList) {
            Optional<Integer> price = getHotelPriceIfPresent(hotel);
            price.ifPresent(prices::add);
            if (prices.size() == 5) break;
        }

        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i + 1)) {
                throw new AssertionError("Hotels are not sorted by lowest price: " + prices);
            }
        }
    }

    private Optional<Integer> getHotelPriceIfPresent(SelenideElement hotel) {
        SelenideElement hotelPrice = hotel.$("span[data-selenium='display-price']").should(visible);
        if (hotelPrice.exists() && hotelPrice.isDisplayed()) {
            String priceText = hotelPrice.getText();
            String price = priceText.replaceAll("\\D", "");
            if (!price.isEmpty()) {
                return Optional.of(Integer.parseInt(price));
            }
        }
        return Optional.empty();
    }
}