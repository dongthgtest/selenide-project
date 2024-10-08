import com.agest.utils.Actions;
import com.agest.utils.Constants;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class TikiTests extends TestBase {

    @Test
    public void testTiki() {
        SelenideElement closeAdsButton = $x("//div[@id='VIP_BUNDLE']//picture");
        if (Actions.isElementDisplayed(closeAdsButton, Constants.DISPLAY_TIMEOUT)) {
            closeAdsButton.click();
            closeAdsButton.shouldNot(visible);
        }
    }
}
