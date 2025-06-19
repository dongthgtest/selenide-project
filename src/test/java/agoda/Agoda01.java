package agoda;

import com.agest.page.agoda.BasePage;
import org.testng.annotations.Test;

public class Agoda01 extends TestBase {
    private final BasePage basePage = new BasePage();

    @Test
    public void test() {
        basePage.selectLanguage("en-us");
        basePage.searchForLocation("Ho Chi Minh");
    }
}
