import com.agest.model.*;
import com.agest.page.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Collections;
import java.util.List;

public class Panel027 extends TestBase {
    private final User adminUser = User.getAdminUser();
    private final LoginPage loginPage = new LoginPage();
    private final DashBoardPage dashBoardPage = new DashBoardPage();
    private final NewPage newPage = new NewPage();
    private final CreatePanelPage createPanelPage = new CreatePanelPage();
    private final ChoosePanelPage choosePanelPage = new ChoosePanelPage();
    private final PanelManagerPage panelManagerPage = new PanelManagerPage();
    private final Series s = Series.getRandomSeries();
    private final Panel panel = new Panel("zbox" + System.currentTimeMillis(), s);
    private final Page page = new Page("Page 1", true);
    private final SoftAssert softAssert = new SoftAssert();

    private List<Panel> panelPreset;

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        dashBoardPage.deletePage();
        dashBoardPage.openPanelManager();
        panelManagerPage.deletePanel(panel);
    }

    @Test(description = "Verify that when 'Choose panels' form is expanded all pre-set panels are populated and sorted correctly")
    public void testPagesCanBeUseForAllUsersOfWorkingRepository() {
        loginPage.login(adminUser);
        dashBoardPage.shouldUserLoginSuccessful(adminUser);

        dashBoardPage.selectGlobalSettingAddPage();
        newPage.createNewPage(page);
        dashBoardPage.shouldPageVisible(page);

        dashBoardPage.selectGlobalSettingCreatePanel();
        createPanelPage.createPanel(panel);

        // Add created panel to charts panel and sort
        panelPreset = Panel.getChartsPanelPreset();
        panelPreset.add(panel);
        Collections.sort(panelPreset);

        dashBoardPage.openChoosePanel();
        choosePanelPage.waitForChoosePanelsPageVisible();

        // Verify that when each panels form is expanded all pre-set panels are populated and sorted correctly
        softAssert.assertEquals(choosePanelPage.getChartsPanel(), panelPreset, PanelType.CHARTS.getErrorMessage());
        softAssert.assertEquals(choosePanelPage.getIndicatorsPanel(), Panel.getIndicatorsPanelPreset(), PanelType.INDICATORS.getErrorMessage());
        softAssert.assertEquals(choosePanelPage.getReportsPanel(), Panel.getReportsPanelPreset(), PanelType.REPORTS.getErrorMessage());
        softAssert.assertEquals(choosePanelPage.getHeatMapsPanel(), Panel.getHeatMapsPanelPreset(), PanelType.HEAT_MAPS.getErrorMessage());
        softAssert.assertAll();
    }
}
