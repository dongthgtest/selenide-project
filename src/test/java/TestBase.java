import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    @BeforeClass
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.reportsFolder = "allure-results";
        Configuration.timeout = 5000;
    }

    @AfterClass
    public void tearDown() {
        closeWebDriver();
    }
}
