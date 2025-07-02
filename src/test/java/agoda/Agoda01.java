package agoda;

import com.agest.page.agoda.HomePage;
import com.agest.page.agoda.SearchResultPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.DateUtils;

import java.time.LocalDate;

public class Agoda01 extends TestBase {
    private final HomePage homePage = new HomePage();

    String destination;
    int targetRooms;
    int targetAdults;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    int expectedHotelsFound;

    @BeforeMethod
    public void PreCondition() {
        destination = "Da Nang";
        targetRooms = 2;
        targetAdults = 4;
        checkInDate = DateUtils.getNextFriday();
        checkOutDate = checkInDate.plusDays(3);
        expectedHotelsFound = 5;
    }

    @Test
    public void test01() {
        homePage.searchAndSelectLocation(destination);
        homePage.findAndSelectDate(checkInDate);
        homePage.findAndSelectDate(checkOutDate);
        homePage.selectRoomQuantity(targetRooms);
        homePage.selectAdultQuantity(targetAdults);

        SearchResultPage searchResultPage = homePage.clickSearchButton();
        searchResultPage.shouldSearchResultDisplayed(expectedHotelsFound, destination);

        searchResultPage.sortByLowestPrice();
        searchResultPage.shouldSearchResultDisplayed(expectedHotelsFound, destination);
        searchResultPage.shouldSortByLowestPrice();
    }
}