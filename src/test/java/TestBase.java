import com.agest.utils.Constants;
import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {

    @BeforeClass
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.reportsFolder = "allure-results";
        Configuration.timeout = 5000;

        open(Constants.TA_DASHBOARD);
    }

    @AfterClass
    public void tearDown() {
        closeWebDriver();
    }
}
