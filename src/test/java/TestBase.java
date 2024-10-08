import com.agest.config.TestConfig;
import com.agest.utils.Constants;
import com.codeborne.selenide.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class TestBase {
    private final TestConfig testConfig = TestConfig.getInstance();

    @BeforeSuite(alwaysRun = true)
    public void beforeTestSuite() {
        log.info("Max retry time : {}", System.getProperty("maxRetryCount"));
        log.info("Grid: {}", System.getProperty("remote"));
        log.info("Browser: {}", System.getProperty("selenide.browser"));
        log.info("Thread count: {}", System.getProperty("threadCount"));

        if (System.getProperty("remote").equals("true")) {
            Configuration.remote = testConfig.remote();
        }
        Configuration.browser = testConfig.getBrowser();
        Configuration.startMaximized = testConfig.isStartMaximized();
        Configuration.reportsFolder = testConfig.getReportFolder();
        Configuration.timeout = testConfig.getTimeout();
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        open(Constants.TA_DASHBOARD);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        closeWebDriver();
    }
}
