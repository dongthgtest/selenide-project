package agoda;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

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
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        open(Configuration.baseUrl);
    }
}
