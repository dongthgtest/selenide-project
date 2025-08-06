package agoda;

import com.agest.model.agoda.FilterHotelCriteria;
import com.agest.model.agoda.SearchHotelCriteria;
import com.agest.page.agoda.HomePage;
import com.agest.page.agoda.SearchResultPage;
import com.agest.utils.DateUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;

public class Agoda02 extends TestBase {
    SoftAssert softAssert = new SoftAssert();
    SearchResultPage searchResultPage;
    HomePage homePage = new HomePage();
    SearchHotelCriteria searchCriteria;
    FilterHotelCriteria filterCriteria;
    Pair<Integer, Integer> priceRange = Pair.of(500000, 1000000);
    int expectedRating = 3;
    String destination = "Da Nang";
    int expectedHotelsFound = 5;

    @BeforeMethod
    public void preCondition() {
        LocalDate checkInDate = DateUtils.getNextFriday();
        LocalDate checkOutDate = checkInDate.plusDays(3);

        searchCriteria = SearchHotelCriteria.builder()
                .destination(destination)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .roomQuantity(2)
                .adultQuantity(4)
                .build();

        filterCriteria = FilterHotelCriteria
                .builder()
                .destination(destination)
                .priceRange(priceRange)
                .rating(expectedRating)
                .build();

        homePage.searchHotel(searchCriteria);

        searchResultPage = homePage.clickSearchButton();
        searchResultPage.shouldSearchResultDisplayed(expectedHotelsFound, searchCriteria.getDestination());
    }

    @Test
    public void verifySearchAndFilterHotelSuccessfully() {
        Pair<Integer, Integer> defaultPriceRange = searchResultPage.getFilterPriceRange();

        searchResultPage.applyFilter(filterCriteria);
        Pair<Integer, Integer> actualPriceRange = searchResultPage.getFilterPriceRange();
        softAssert.assertEquals(priceRange, actualPriceRange);

        searchResultPage.verifySelectedRatingFilterHighlighted(this.expectedRating);
        searchResultPage.verifyFilteredResult(filterCriteria, expectedHotelsFound);

        searchResultPage.setPriceRange(defaultPriceRange);
        actualPriceRange = searchResultPage.getFilterPriceRange();
        softAssert.assertEquals(defaultPriceRange, actualPriceRange);
        softAssert.assertAll();
    }
}
