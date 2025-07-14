package vietjet;

import com.agest.utils.I18n;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class TestBase {

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        open("/");

        String language = System.getProperty("language", "en");
        I18n.getInstance().loadLanguage(Locale.of(language));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        closeWebDriver();
    }
}
