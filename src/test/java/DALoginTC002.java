import com.agest.listener.RetryAnalyzer;
import com.agest.model.User;
import com.agest.page.LoginPage;
import com.agest.utils.AlertUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class DALoginTC002 extends TestBase {
    private final User invalidUser = new User("abc", "abc");
    private final LoginPage loginPage = new LoginPage();

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        loginPage.acceptAlert();
    }

    @Test(description = "Verify that user fails to login specific repository successfully via Dashboard login page with incorrect credentials",
            retryAnalyzer = RetryAnalyzer.class)
    public void TC002() {
        loginPage.login(invalidUser);
        String alertInvalidMessage = "Username or password is invalid";
        Assert.assertEquals(AlertUtils.getAlertContent(), alertInvalidMessage);
    }
}
