import com.agest.listener.RetryAnalyzer;
import com.agest.model.User;
import com.agest.page.LoginPage;
import com.agest.utils.AlertUtils;
import com.agest.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class DaLoginTC002 extends TestBase {
    private final LoginPage loginPage = new LoginPage();
    private final User invalidUser = new User("abc", "abc");

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        open(Constants.TA_DASHBOARD);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        AlertUtils.closeAlert();
    }

    @Test(description = "Verify that user fails to login specific repository successfully via Dashboard login page with incorrect credentials",
            retryAnalyzer = RetryAnalyzer.class)
    public void TC002() {
        loginPage.loginUser(invalidUser);
        String alertInvalidMessage = "Username or password is invalid";
        Assert.assertEquals(AlertUtils.getAlertContent(), alertInvalidMessage);
    }
}
