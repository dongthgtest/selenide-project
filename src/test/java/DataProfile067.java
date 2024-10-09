import com.agest.model.User;
import com.agest.page.ta.DashBoardPage;
import com.agest.page.ta.DataProfilePage;
import com.agest.page.ta.LoginPage;
import com.agest.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;

public class DataProfile067 extends TestBase {
    private final User user = User.getUser2();
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();
    private final DataProfilePage dataProfilePage = new DataProfilePage();

    @Test(description = "Verify that Data Profiles are listed alphabetically")
    public void verifyDataProfilesAreListedAlphabetically() {
        open(Constants.TA_DASHBOARD);
        loginPage.login(user, Constants.TEST_REPO);
        dashBoardPage.openDataProfiles();
        List<String> actualDataProfiles = dataProfilePage.getDataProfiles();
        List<String> expectedDataProfiles = dataProfilePage.getDataProfiles();
        Collections.sort(expectedDataProfiles);
        Assert.assertEquals(actualDataProfiles, expectedDataProfiles, "The data is not listed alphabetically!");
    }
}
