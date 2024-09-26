import com.agest.config.TestConfig;
import com.agest.utils.Constants;
import com.codeborne.selenide.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class TestBase {
    private final TestConfig testConfig = TestConfig.getInstance();

    @Parameters({"remote"})
    @BeforeClass
    public void setUp(@Optional("") String remote) {
        log.info("------------------------------------------remote=" + System.getProperty("remote"));
        if (System.getProperty("remote").equals("true")) {
            Configuration.remote = testConfig.remote();
        }
        Configuration.browser = testConfig.getBrowser();
        Configuration.startMaximized = testConfig.isStartMaximized();
        Configuration.reportsFolder = testConfig.getReportFolder();
        Configuration.timeout = testConfig.getTimeout();

        open(Constants.TA_DASHBOARD);
    }

    @AfterClass
    public void tearDown() {
        log.info("------------------------------------------REMOTE=" + System.getProperty("remote"));
        closeWebDriver();
    }
}
