package sele2;

import com.agest.model.sele2.AlertMessages;
import com.agest.model.sele2.Page;
import com.agest.model.sele2.User;
import com.agest.page.DashBoardPage;
import com.agest.page.LoginPage;
import com.agest.page.NewPage;
import com.agest.utils.AlertUtils;
import com.agest.utils.Constants;
import com.agest.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MainPage017 extends TestBase {
    private final User user = User.getUser2();
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();
    private final NewPage newPage = new NewPage();
    private final Page parentPage = new Page("Test");
    private final Page childPage = new Page("Test Child");
    private final Page overviewPage = new Page("Overview");
    private final String warningRemovePageMessage = AlertMessages.WARNING_REMOVE_PAGE.getMessage();
    private final String confirmRemovePageMessage = AlertMessages.CONFIRM_REMOVE_PAGE.getMessage();

    @Test(description = "Verify that user can remove any main parent page except 'Overview' page successfully " +
            "and the order of pages stays persistent as long as there is not children page under")
    public void testUserCanRemovePageWithNoChildrenPage() {
        loginPage.login(user, Constants.TEST_REPO);
        dashBoardPage.shouldUserLoginSuccessful(user);

        dashBoardPage.selectGlobalSettingAddPage();
        newPage.createNewPage(parentPage);
        dashBoardPage.shouldPageVisible(parentPage);

        dashBoardPage.selectGlobalSettingAddPage();
        newPage.createNewChildPage(childPage, parentPage);
        dashBoardPage.openPage(parentPage);
        dashBoardPage.shouldChildPageVisible(childPage);

        dashBoardPage.clickDeletePage();
        String alertMessage = AlertUtils.getAlertContent();
        Assert.assertEquals(alertMessage, confirmRemovePageMessage,
                Utils.getErrorMessage(alertMessage, confirmRemovePageMessage));
        dashBoardPage.acceptAlert();

        alertMessage = AlertUtils.getAlertContent();
        String expectedMessage = String.format(warningRemovePageMessage, parentPage.getPageName());
        Assert.assertEquals(alertMessage, expectedMessage, Utils.getErrorMessage(alertMessage, expectedMessage));
        dashBoardPage.acceptAlert();

        dashBoardPage.openChildPage(childPage, parentPage);
        dashBoardPage.clickDeletePage();
        alertMessage = AlertUtils.getAlertContent();
        Assert.assertEquals(alertMessage, confirmRemovePageMessage,
                Utils.getErrorMessage(alertMessage, confirmRemovePageMessage));

        dashBoardPage.acceptAlert();
        dashBoardPage.shouldChildPageDeleted(childPage, parentPage);

        dashBoardPage.openPage(parentPage);
        dashBoardPage.clickDeletePage();
        alertMessage = AlertUtils.getAlertContent();
        Assert.assertEquals(alertMessage, confirmRemovePageMessage,
                Utils.getErrorMessage(alertMessage, confirmRemovePageMessage));

        dashBoardPage.acceptAlert();
        dashBoardPage.shouldPageDisappear(parentPage);

        dashBoardPage.openPage(overviewPage);
        dashBoardPage.shouldDeleteButtonDisappears();
    }
}
