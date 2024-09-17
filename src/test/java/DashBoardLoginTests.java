import com.agest.model.User;
import com.agest.page.DashBoardPage;
import com.agest.page.LoginPage;
import com.agest.utils.Constants;
import com.codeborne.selenide.testng.ScreenShooter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

@Listeners({ScreenShooter.class})
public class DashBoardLoginTests extends TestBase {
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();
    private final User adminUser = new User("administrator", "");
    private final User invalidUser = new User("abc", "abc");

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        open(Constants.TA_DASHBOARD);
    }

    @Test(description = "Verify that user can login specific repository successfully via Dashboard login page with correct credentials")
    public void TC001() {
        loginPage.loginUser(adminUser);
        dashBoardPage.shouldUserLoginSuccessful(adminUser.getUsername());
    }

    @Test(description = "Verify that user fails to login specific repository successfully via Dashboard login page with incorrect credentials")
    public void TC002() {
        loginPage.loginUser(invalidUser);
    }
}
