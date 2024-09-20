import com.agest.model.GlobalSetting;
import com.agest.model.User;
import com.agest.page.DashBoardPage;
import com.agest.page.LoginPage;
import com.agest.page.NewPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class DAMainPageTC014 extends TestBase {
    private final User user1 = User.getUser1();
    private final User user2 = User.getUser2();
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();
    private final NewPage newPage = new NewPage();
    private final String newPageName = "DongPage";

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        dashBoardPage.logOut(user1);
        loginPage.loginUser(user2);

        dashBoardPage.openPage(newPageName);
        dashBoardPage.hoverGlobalSetting();
        dashBoardPage.deletePage();

        dashBoardPage.shouldPageDisappear(newPageName);
    }

    @Test(description = "Verify that 'Public' pages can be visible and accessed by all users of working repository")
    public void testPagesCanBeUseForAllUsersOfWorkingRepository() {
        loginPage.loginUser(user2);
        dashBoardPage.shouldUserLoginSuccessful(user2.getUsername());

        dashBoardPage.selectGlobalSetting(GlobalSetting.ADD_PAGE);
        newPage.inputPageNameField(newPageName);
        newPage.checkPublicCheckBox();
        newPage.clickOkButton();
        dashBoardPage.shouldPageVisible(newPageName);

        dashBoardPage.logOut(user2);
        loginPage.loginUser(user1);
        dashBoardPage.shouldPageVisible(newPageName);
    }
}
