package agoda;

import com.codeborne.selenide.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class TestBase {
    @BeforeSuite(alwaysRun = true)
    public void beforeTestSuite() {
        log.info("Max retry time : {}", System.getProperty("maxRetryCount"));
        log.info("Grid: {}", System.getProperty("remote"));
        log.info("Browser: {}", System.getProperty("selenide.browser"));
        log.info("Thread count: {}", System.getProperty("threadCount"));
        log.info("Language: {}", System.getProperty("language"));

        Configuration.browser = System.getProperty("selenide.browser", "chrome");
        Configuration.reportsFolder = System.getProperty("selenide.reportsFolder", "allure-results");
        Configuration.timeout = Long.parseLong(System.getProperty("selenide.timeout", "5000"));
        Configuration.baseUrl = System.getProperty("selenide.baseUrl", "https://agoda.com");
        Configuration.remote = System.getProperty("remote", null);
        Configuration.browserSize = System.getProperty("selenide.browserSize", "1920x1080");
    }

    @BeforeTest(alwaysRun = true)
    public void setUp() {
        open(Configuration.baseUrl);
    }
}
