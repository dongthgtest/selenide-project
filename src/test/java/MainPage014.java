import com.agest.model.Page;
import com.agest.model.User;
import com.agest.page.DashBoardPage;
import com.agest.page.LoginPage;
import com.agest.page.NewPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class MainPage014 extends TestBase {
    private final User firstUser = User.getUser1();
    private final User secondUser = User.getUser3();
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();
    private final NewPage newPage = new NewPage();
    private final Page page = new Page("" + System.currentTimeMillis(), true);
    private final String testRepo = "Test";

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        dashBoardPage.logOut();
        loginPage.login(firstUser, testRepo);

        dashBoardPage.openPage(page);
        dashBoardPage.deletePage();

        dashBoardPage.shouldPageDisappear(page);
    }

    @Test(description = "Verify that 'Public' pages can be visible and accessed by all users of working repository")
    public void testPagesCanBeUseForAllUsersOfWorkingRepository() {
        loginPage.login(firstUser, testRepo);
        dashBoardPage.shouldUserLoginSuccessful(firstUser);

        dashBoardPage.selectGlobalSettingAddPage();
        newPage.createNewPage(page);
        dashBoardPage.shouldPageVisible(page);

        dashBoardPage.logOut();
        loginPage.login(secondUser, testRepo);
        dashBoardPage.shouldPageVisible(page);
    }
}
