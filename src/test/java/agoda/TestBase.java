package agoda;

import com.agest.utils.I18n;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class TestBase {
    @BeforeSuite(alwaysRun = true)
    public void beforeTestSuite() {
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
}
