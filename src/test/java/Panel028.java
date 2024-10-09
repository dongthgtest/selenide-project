import com.agest.model.User;
import com.agest.page.ta.DashBoardPage;
import com.agest.page.ta.LoginPage;
import com.agest.page.ta.PanelManagerPage;
import com.agest.utils.Constants;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class Panel028 extends TestBase {
    private final User adminUser = User.getAdminUser();
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();
    private final PanelManagerPage panelManagerPage = new PanelManagerPage();

    @Test(description = "Verify that when 'Choose panels' form is expanded all pre-set panels are populated and sorted correctly")
    public void testPagesCanBeUseForAllUsersOfWorkingRepository() {
        open(Constants.TA_DASHBOARD);
        loginPage.login(adminUser);
        dashBoardPage.shouldUserLoginSuccessful(adminUser);
        dashBoardPage.openPanelManager();
        panelManagerPage.addNewPanel();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();

        panelManagerPage.shouldAddNewButtonBlocked();
        dashBoardPage.shouldAllOtherElementBlockedOrLocked(currentUrl);
    }
}
