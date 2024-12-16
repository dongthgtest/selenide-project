package sele2;

import com.agest.model.sele2.User;
import com.agest.page.DashBoardPage;
import com.agest.page.LoginPage;
import org.testng.annotations.Test;

public class Login001 extends TestBase {
    private final User adminUser = User.getAdminUser();
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();

    @Test(description = "Verify that user can login specific repository successfully via Dashboard login page with correct credentials")
    public void TC001() {
        loginPage.login(adminUser);
        dashBoardPage.shouldUserLoginSuccessful(adminUser);
    }
}
