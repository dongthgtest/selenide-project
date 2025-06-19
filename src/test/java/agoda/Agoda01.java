package agoda;

import com.agest.page.agoda.HomePage;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class Agoda01 extends TestBase {
    private final HomePage homePage = new HomePage();

    @Test
    public void test01() {
        homePage.selectLanguage("English");
        homePage.searchAndSelectLocation("Ho Chi Minh");
        homePage.findAndSelectCheckInDate(LocalDate.parse("2026-01-01"));
        homePage.findAndSelectCheckOutDate(LocalDate.parse("2026-01-10"));
    }
}
