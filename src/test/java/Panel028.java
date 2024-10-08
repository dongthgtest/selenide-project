import com.agest.model.User;
import com.agest.page.DashBoardPage;
import com.agest.page.LoginPage;
import com.agest.page.PanelManagerPage;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;

public class Panel028 extends TestBase {
    private final User adminUser = User.getAdminUser();
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();
    private final PanelManagerPage panelManagerPage = new PanelManagerPage();

    @Test(description = "Verify that when 'Choose panels' form is expanded all pre-set panels are populated and sorted correctly")
    public void testPagesCanBeUseForAllUsersOfWorkingRepository() {
        loginPage.login(adminUser);
        dashBoardPage.shouldUserLoginSuccessful(adminUser);
        dashBoardPage.openPanelManager();
        panelManagerPage.addNewPanel();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();

        panelManagerPage.shouldAddNewButtonBlocked();
        dashBoardPage.shouldAllOtherElementBlockedOrLocked(currentUrl);
    }
}
