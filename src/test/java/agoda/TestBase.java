package agoda;

import com.agest.utils.I18n;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class TestBase {

    @BeforeSuite(alwaysRun = true)
    public void beforeTestSuite() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--no-first-run");
        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-extensions");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        // Set headless mode from system property
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "false"));

        // Only use a custom Chrome profile locally (skip in CI)
        if (!isCiEnvironment()) {
            try {
                Path tempProfile = Files.createTempDirectory("chrome-profile-");
                options.addArguments("--user-data-dir=" + tempProfile.toAbsolutePath());
                System.out.println("Using Chrome user-data-dir: " + tempProfile.toAbsolutePath());
            } catch (Exception e) {
                throw new RuntimeException("Failed to create temp directory for Chrome profile", e);
            }
        } else {
            log.info("Running in CI — skipping custom user-data-dir");
        }

        Configuration.browserCapabilities = options;
        Configuration.browserSize = "1920x1080";

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
        );

        String language = System.getProperty("language", "en");
        I18n.getInstance().loadLanguage(Locale.of(language));
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        open(Configuration.baseUrl);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        log.info("Test completed. Closing browser.");
        closeWebDriver();

        // Small delay to ensure Chrome releases the profile lock
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    private boolean isCiEnvironment() {
        return "true".equalsIgnoreCase(System.getenv("CI"));
    }
}