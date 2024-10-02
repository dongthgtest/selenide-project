import com.agest.model.User;
import com.agest.page.DashBoardPage;
import com.agest.page.DataProfilePage;
import com.agest.page.LoginPage;
import com.agest.utils.Constants;
import com.agest.utils.JsonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class DataProfile065 extends TestBase {
    private final User user = User.getUser2();
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();
    private final DataProfilePage dataProfilePage = new DataProfilePage();
    private final List<String> presetDataProfiles = JsonUtils.toList(Constants.PRESET_DATA_PROFILES, String.class);

    @Test(description = "Verify that all Pre-set Data Profiles are populated correctly")
    public void verifyPresetDataProfilesArePopulatedCorrectly() {
        loginPage.login(user, Constants.TEST_REPO);
        dashBoardPage.openDataProfiles();
        List<String> actualDataProfiles = dataProfilePage.getDataProfiles();
        Assert.assertEquals(presetDataProfiles, actualDataProfiles, "The data not match with preset!");
    }
}
