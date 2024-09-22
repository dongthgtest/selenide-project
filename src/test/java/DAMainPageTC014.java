import com.agest.model.Page;
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
    private final Page page = new Page("" + System.currentTimeMillis(), true);
    private final String testRepo = "Test";

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        dashBoardPage.logOut();
        loginPage.login(user2, testRepo);

        dashBoardPage.openPage(page);
        dashBoardPage.deletePage();

        dashBoardPage.shouldPageDisappear(page);
    }

    @Test(description = "Verify that 'Public' pages can be visible and accessed by all users of working repository")
    public void testPagesCanBeUseForAllUsersOfWorkingRepository() {
        loginPage.login(user2, testRepo);
        dashBoardPage.shouldUserLoginSuccessful(user2.getUsername());

        dashBoardPage.selectGlobalSettingAddPage();
        newPage.createNewPage(page);
        dashBoardPage.shouldPageVisible(page);

        dashBoardPage.logOut();
        loginPage.login(user1, testRepo);
        dashBoardPage.shouldPageVisible(page);
    }
}
