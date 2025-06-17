package agoda;

import com.agest.page.agoda.BasePage;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class Agoda01 extends TestBase {
    private final BasePage basePage = new BasePage();

    @Test
    public void test() {
        basePage.selectLanguage("English");
        basePage.searchAndSelectLocation("Ho Chi Minh");
        basePage.findAndSelectDateFromCalendar(LocalDate.parse("2026-01-01"));
    }
}
