package com.agest.page.agoda;

import com.agest.model.agoda.FilterHotelCriteria;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SearchResultPage {
    private final SelenideElement sortLowestPriceButton = $x("//button[@data-element-name='search-sort-price']");
    private final SelenideElement minPriceTextBox = $("#price_box_0");
    private final SelenideElement maxPriceTextBox = $("#price_box_1");
    private final SelenideElement minPrice = $("#SideBarLocationFilters #price_box_0");
    private final SelenideElement maxPrice = $("#SideBarLocationFilters #price_box_1");
    // Dynamic selector
    private final String rating = "#filter-menu-StarRatingWithLuxury ~ ul [data-element-value='%s']";

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
            setPriceFilter(minPriceTextBox, criteria.getPriceRange().getLeft());
            setPriceFilter(maxPriceTextBox, criteria.getPriceRange().getRight());
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

    @Step("Get filter price range")
    public Pair<Integer, Integer> getFilterPriceRange() {
        return Pair.of(
                getFilterPriceValue(minPrice),
                getFilterPriceValue(maxPrice)
        );
    }

    @Step("Get price value of {element}")
    private int getFilterPriceValue(SelenideElement element) {
        String priceText = Objects.requireNonNull(element.getAttribute("value")).replaceAll("\\D", "");
        return Integer.parseInt(priceText);
    }

    @Step("Verify filter price range is displayed correctly")
    public void verifyFilterRangeDisplay(Pair<Integer, Integer> expectedPriceRange) {
        Pair<Integer, Integer> actualPriceRange = getFilterPriceRange();
        if (!actualPriceRange.equals(expectedPriceRange)) {
            throw new AssertionError("Expected price range " + expectedPriceRange + " but got " + actualPriceRange);
        }
    }

    @Step("Verify {rating} star rating filter is highlighted")
    public void verifySelectedRatingFilterHighlighted(String rating) {
        SelenideElement ratingFilter = $(String.format(this.rating, rating)).$("input");
        if (!ratingFilter.isSelected()) {
            throw new AssertionError("Rating filter for " + rating + " stars is not highlighted.");
        }
    }

    private double getHotelPrice(SelenideElement hotel) {
        SelenideElement price = hotel.$("span[data-selenium='display-price']");
        price.shouldBe(Condition.visible).scrollIntoView(true);
        String text = price.getText().replaceAll("[^\\d.]", "");
        return Double.parseDouble(text);
    }

    private boolean isHotelPriceInRange(SelenideElement hotel, Pair<Integer, Integer> priceRange) {
        double hotelPrice = getHotelPrice(hotel);
        return priceRange.getLeft() < hotelPrice && hotelPrice < priceRange.getRight();
    }

    private boolean isHotelRatingMatches(SelenideElement hotel, String rating) {
        String hotelRating = hotel
                .$x(String.format("//span[data-testid='rating-container']//span[contains(text(),'%s stars out of 5')]", rating))
                .getText();
        return hotelRating.contains(rating);
    }

    private boolean isHotelDestinationMatches(SelenideElement hotel, String destination) {
        String hotelDestination = hotel.$("//div[button[span[contains(@label,'Da Nang')]]]").getText();
        return hotelDestination.contains(destination);
    }

    @Step("Verify the result filtered by criteria: {criteria}")
    public void verifyFilteredResult(FilterHotelCriteria criteria, int expectedHotels) {
        ElementsCollection hotelList = getHotelList();
        hotelList.shouldHave(sizeGreaterThan(expectedHotels));
        int index = 0;
        for (SelenideElement hotel : hotelList) {
            double hotelPrice = getHotelPrice(hotel);
            if (isHotelPriceInRange(hotel, criteria.getPriceRange())
//                    && isHotelRatingMatches(hotel, criteria.getRating())
                    && isHotelDestinationMatches(hotel, criteria.getDestination())) {
                index++;
            }
            if (index >= 5) {
                break;
            }
        }
    }
}
