package agoda;

import com.agest.page.agoda.HomePage;
import com.agest.page.agoda.SearchResultPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.DateUtils;

import java.time.LocalDate;

public class Agoda01 extends TestBase {
    private final HomePage homePage = new HomePage();

    private String destination;
    private int targetRooms;
    private int targetAdults;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int expectedHotelsFound;

    @BeforeMethod
    public void preCondition() {
        destination = "Da Nang";
        targetRooms = 2;
        targetAdults = 4;
        checkInDate = DateUtils.getNextFriday();
        checkOutDate = checkInDate.plusDays(3);
        expectedHotelsFound = 5;
    }

    @Test
    public void verifySearchAndSortHotelSuccessfully() {
        homePage.searchAndSelectLocation(destination);
        homePage.findAndSelectDate(checkInDate);
        homePage.findAndSelectDate(checkOutDate);
        homePage.selectRoomQuantity(targetRooms);
        homePage.selectAdultQuantity(targetAdults);

        SearchResultPage searchResultPage = homePage.clickSearchButton();
        searchResultPage.shouldSearchResultDisplayed(expectedHotelsFound, destination);

        searchResultPage.sortByLowestPrice();
        searchResultPage.shouldSearchResultDisplayed(expectedHotelsFound, destination);
        searchResultPage.shouldSortByLowestPrice(expectedHotelsFound);
    }
}
