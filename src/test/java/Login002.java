import com.agest.listener.RetryAnalyzer;
import com.agest.model.AlertMessages;
import com.agest.model.User;
import com.agest.page.ta.LoginPage;
import com.agest.utils.AlertUtils;
import com.agest.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class Login002 extends TestBase {
    private final User invalidUser = new User("abc", "abc");
    private final LoginPage loginPage = new LoginPage();

    @Test(description = "Verify that user fails to login specific repository successfully via Dashboard login page with incorrect credentials",
            retryAnalyzer = RetryAnalyzer.class)
    public void TC002() {
        open(Constants.TA_DASHBOARD);
        loginPage.login(invalidUser);
        Assert.assertEquals(AlertUtils.getAlertContent(), AlertMessages.INVALID_USERNAME_PASSWORD.getMessage());
    }
}
