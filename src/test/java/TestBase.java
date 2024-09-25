import com.agest.utils.Constants;
import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {

    @Parameters({"isEnableGrid"})
    @BeforeClass
    public void setUp(String isEnableGrid) {
        Configuration.remote = isEnableGrid.equals("true")
                ? Constants.GRID_HUB_URL
                : null;
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
        Configuration.reportsFolder = "allure-results";
        Configuration.timeout = 5000;

        open(Constants.TA_DASHBOARD);
    }

    @AfterClass
    public void tearDown() {
        closeWebDriver();
    }
}
