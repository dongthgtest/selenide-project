package agoda;

import com.agest.page.agoda.HomePage;
import org.testng.annotations.Test;
import utils.DateUtils;

import java.time.LocalDate;

public class Agoda01 extends TestBase {
    private final HomePage homePage = new HomePage();

    @Test
    public void test01() {
        homePage.selectLanguage("English");
        homePage.searchAndSelectLocation("Ho Chi Minh");
        LocalDate checkInDate = DateUtils.getNextFriday();
        LocalDate checkOutDate = checkInDate.plusDays(3);
        homePage.findAndSelectDate(checkInDate);
        homePage.findAndSelectDate(checkOutDate);
        homePage.selectRoomQuantity(2);
        homePage.selectAdultQuantity(4);
    }
}
