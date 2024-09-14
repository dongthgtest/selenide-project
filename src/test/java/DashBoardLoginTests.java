import com.agest.model.User;
import com.agest.page.LoginPage;
import com.agest.page.TAHomePage;
import com.agest.utils.Constants;
import com.codeborne.selenide.testng.ScreenShooter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.open;

@Listeners({ScreenShooter.class})
public class DashBoardLoginTests extends TestBase {
    SoftAssert softAssert = new SoftAssert();
    private final LoginPage loginPage = new LoginPage();
    private final TAHomePage taHomePage = new TAHomePage();
    private final User adminUser = new User("administrator", "");
    private final User invalidUser = new User("abc", "abc");

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        open(Constants.TA_DASHBOARD);
    }

    @Test(description = "Verify that user can login specific repository successfully via Dashboard login page with correct credentials")
    public void TC001() {
        loginPage.loginUser(adminUser);
        softAssert.assertTrue(loginPage.isLoggingSuccessful());
    }

    @Test(description = "Verify that user fails to login specific repository successfully via Dashboard login page with incorrect credentials")
    public void TC002() {
        loginPage.loginUser(invalidUser);
        softAssert.assertFalse(taHomePage.isLoginSuccessful(invalidUser.getUsername()));
    }
}
