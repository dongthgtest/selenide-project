import com.agest.page.DashBoardPage;
import com.agest.page.LoginPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class DALoginTC001 extends TestBase {
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        dashBoardPage.logOut(adminUser);
    }

    @Test(description = "Verify that user can login specific repository successfully via Dashboard login page with correct credentials")
    public void TC001() {
        loginPage.loginUser(adminUser);
        dashBoardPage.shouldUserLoginSuccessful(adminUser.getUsername());
    }
}
