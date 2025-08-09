package agoda;

import com.agest.constant.agoda.Facility;
import com.agest.constant.agoda.Review;
import com.agest.model.agoda.FilterHotelCriteria;
import com.agest.model.agoda.SearchHotelCriteria;
import com.agest.page.agoda.HomePage;
import com.agest.page.agoda.HotelDetailPage;
import com.agest.page.agoda.SearchResultPage;
import com.agest.utils.DateUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.switchTo;

public class Agoda03 extends TestBase {

    SoftAssert softAssert = new SoftAssert();
    SearchResultPage searchResultPage;
    HomePage homePage = new HomePage();
    HotelDetailPage hotelDetailPage = new HotelDetailPage();
    SearchHotelCriteria searchCriteria;
    FilterHotelCriteria filterCriteria;
    LocalDate checkInDate = DateUtils.getNextFriday().plusWeeks(1);
    LocalDate checkOutDate = checkInDate.plusDays(3);
    String destination = "Da Nang";
    String hotelName;
    List<Pair<Review, Float>> reviews;

    @BeforeMethod
    public void preCondition() {

        searchCriteria = SearchHotelCriteria.builder()
                .destination(destination)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .roomQuantity(2)
                .adultQuantity(4)
                .build();
    }


    @Test(
            description = "Search, filter and verify hotel details successfully"
    )
    public void verifySearchFilterAndHotelDetails() {
        homePage.searchHotel(searchCriteria);

        searchResultPage = homePage.clickSearchButton();

        searchResultPage.shouldSearchResultDisplayed(5, searchCriteria.getDestination());

        searchResultPage.filterByFacilities(Facility.SWIMMING_POOL);

        hotelName = searchResultPage.getHotelName(5);

        searchResultPage.openHotelDetails(5);

        switchTo().window(2);

        hotelDetailPage.hotelNameShouldBe(hotelName);

        hotelDetailPage.hotelAddressShouldContain(destination);

        hotelDetailPage.facilityShouldContain(Facility.SWIMMING_POOL);

        closeWindow();

        switchTo().window(1);

        reviews = searchResultPage.getHotelReviews(1);

        hotelName = searchResultPage.getHotelName(1);

        searchResultPage.openHotelDetails(1);

        switchTo().window(2);

        hotelDetailPage.hotelNameShouldBe(hotelName);

        hotelDetailPage.hotelAddressShouldContain(destination);

        hotelDetailPage.facilityShouldContain(Facility.SWIMMING_POOL);

        hotelDetailPage.verifyReviewScores(reviews);
    }

}
