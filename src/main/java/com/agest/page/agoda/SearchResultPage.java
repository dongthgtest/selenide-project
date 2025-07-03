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
        for (int i = 0; i < Math.min(expectedHotelsFound, hotelList.size()); i++) {
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
        while (prices.size() < expectedHotels  && index < hotelList.size()) {
            SelenideElement hotel = hotelList.get(index);
            hotel.scrollIntoView("{block: 'center'}");
            Optional<Integer> price = getHotelPriceIfPresent(hotel);
            price.ifPresent(prices::add);
            index++;
        }
        if (prices.size() < expectedHotels) {
            throw new AssertionError("Could not find 5 hotel prices. Only found: " + prices.size() + " prices: " + prices);
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
}
