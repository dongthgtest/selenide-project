package vietjet;

import com.agest.utils.Constants;
import com.codeborne.selenide.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import static com.codeborne.selenide.Selenide.closeWebDriver;

@Slf4j
public class TestBase {
    protected final String language = System.getProperty("language", "en");

    @BeforeSuite(alwaysRun = true)
    public void beforeTestSuite() {
        log.info("Max retry time : {}", System.getProperty("maxRetryCount"));
        log.info("Grid: {}", System.getProperty("remote"));
        log.info("Browser: {}", System.getProperty("selenide.browser"));
        log.info("Thread count: {}", System.getProperty("threadCount"));
        log.info("Language: {}", System.getProperty("language"));

        Configuration.browser = "chrome";
        Configuration.reportsFolder = "allure-results";
        Configuration.timeout = 5000;
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        Configuration.baseUrl = Constants.VIET_JET_EN_URL;
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        closeWebDriver();
    }
}
