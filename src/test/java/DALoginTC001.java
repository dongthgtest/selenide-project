import com.agest.model.User;
import com.agest.page.DashBoardPage;
import com.agest.page.LoginPage;
import com.agest.utils.Constants;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class DALoginTC001 extends TestBase {
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();
    private final User adminUser = new User("administrator", "");

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        open(Constants.TA_DASHBOARD);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        dashBoardPage.logOut(adminUser.getUsername());
    }

    @Test(description = "Verify that user can login specific repository successfully via Dashboard login page with correct credentials")
    public void TC001() {
        loginPage.loginUser(adminUser);
        dashBoardPage.shouldUserLoginSuccessful(adminUser.getUsername());
    }
}
