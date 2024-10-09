import com.agest.model.User;
import com.agest.page.ta.DashBoardPage;
import com.agest.page.ta.LoginPage;
import com.agest.utils.Constants;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class Login001 extends TestBase {
    private final User adminUser = User.getAdminUser();
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();

    @Test(description = "Verify that user can login specific repository successfully via Dashboard login page with correct credentials")
    public void TC001() {
        open(Constants.TA_DASHBOARD);
        loginPage.login(adminUser);
        dashBoardPage.shouldUserLoginSuccessful(adminUser);
    }
}
