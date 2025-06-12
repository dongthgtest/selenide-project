package vietjet;

import com.agest.model.vietjet.DepartureLocation;
import com.agest.page.vietjet.HomePage;
import com.agest.utils.Constants;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class VJ01 extends TestBase {
    private final HomePage homePage = new HomePage();

    @Test(description = "Search and choose tickets on a specific day successfully")
    public void testSearchAndChooseTicketWorkCorrectlyOnSpecificDay() {
        open(Constants.VIET_JET_EN_URL);
        WebDriverRunner.getWebDriver().manage().window().maximize();
        homePage.acceptCookie();
        homePage.departureFrom(DepartureLocation.HO_CHI_MINH);
        homePage.returnTo(DepartureLocation.HA_NOI);
    }
}
