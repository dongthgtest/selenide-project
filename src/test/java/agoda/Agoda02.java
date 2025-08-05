package agoda;

import com.agest.model.agoda.FilterHotelCriteria;
import com.agest.model.agoda.SearchHotelCriteria;
import com.agest.page.agoda.HomePage;
import com.agest.page.agoda.SearchResultPage;
import com.agest.utils.DateUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class Agoda02 extends TestBase {
    SearchResultPage searchResultPage;
    HomePage homePage = new HomePage();
    SearchHotelCriteria searchCriteria;
    FilterHotelCriteria filterCriteria;

    @BeforeMethod
    public void preCondition() {
        LocalDate checkInDate = DateUtils.getNextFriday();
        LocalDate checkOutDate = checkInDate.plusDays(3);

        searchCriteria = SearchHotelCriteria.builder()
                .destination("Da Nang")
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .roomQuantity(2)
                .adultQuantity(4)
                .build();

        filterCriteria = FilterHotelCriteria
                .builder()
                .priceRange(Pair.of(500000, 1000000))
                .rating("3")
                .build();

        int expectedHotelsFound = 5;

        homePage.searchHotel(searchCriteria);

        searchResultPage = homePage.clickSearchButton();
        searchResultPage.shouldSearchResultDisplayed(expectedHotelsFound, searchCriteria.getDestination());
    }

    @Test
    public void verifySearchAndFilterHotelSuccessfully() {
        searchResultPage.applyFilter(filterCriteria);
    }
}
