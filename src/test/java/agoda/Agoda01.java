package agoda;

import com.agest.model.agoda.SearchHotelCriteria;
import com.agest.page.agoda.HomePage;
import com.agest.page.agoda.SearchResultPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.agest.utils.DateUtils;

import java.time.LocalDate;

public class Agoda01 extends TestBase {
    SearchResultPage searchResultPage;
    HomePage homePage = new HomePage();
    SearchHotelCriteria criteria;
    private int expectedHotelsFound;

    @BeforeMethod
    public void preCondition() {
        LocalDate checkInDate = DateUtils.getNextFriday();
        LocalDate checkOutDate = checkInDate.plusDays(3);
        criteria = SearchHotelCriteria.builder()
                .destination("Da Nang")
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .roomQuantity(2)
                .adultQuantity(4)
                .build();

        expectedHotelsFound = 5;

        homePage.searchHotel(criteria);

        searchResultPage = homePage.clickSearchButton();
        searchResultPage.shouldSearchResultDisplayed(expectedHotelsFound, criteria.getDestination());
    }

    @Test
    public void verifySearchAndSortHotelSuccessfully() {
        searchResultPage.sortByLowestPrice();
        searchResultPage.shouldSearchResultDisplayed(expectedHotelsFound, criteria.getDestination());
        searchResultPage.shouldSortByLowestPrice(expectedHotelsFound);
    }
}
