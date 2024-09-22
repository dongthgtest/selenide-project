import com.agest.model.*;
import com.agest.page.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class DAPanelTC027 extends TestBase {
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
    private List<Panel> presetCharts;

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        dashBoardPage.deletePage();
        dashBoardPage.openPanelManager();
        panelManagerPage.deletePanel(panel);
    }

    @Test(description = "Verify that when 'Choose panels' form is expanded all pre-set panels are populated and sorted correctly")
    public void testPagesCanBeUseForAllUsersOfWorkingRepository() {
        loginPage.login(adminUser);
        dashBoardPage.shouldUserLoginSuccessful(adminUser.getUsername());

        dashBoardPage.selectGlobalSettingAddPage();
        newPage.createNewPage(page);
        dashBoardPage.shouldPageVisible(page);

        dashBoardPage.selectGlobalSettingCreatePanel();
        createPanelPage.createPanel(panel);

        // Add created panel to charts panel and sort
        presetCharts = Panel.getChartsPanel();
        presetCharts.add(panel);
        Collections.sort(presetCharts);

        dashBoardPage.openChoosePanel();
        choosePanelPage.waitForChoosePanelsPageVisible();

        // Verify that when each panels form is expanded all pre-set panels are populated and sorted correctly
        Assert.assertEquals(choosePanelPage.getChartsPanel(), presetCharts, PanelType.CHARTS.getErrorMessage());
        Assert.assertEquals(choosePanelPage.getIndicatorsPanel(), Panel.getIndicatorsPanel(), PanelType.INDICATORS.getErrorMessage());
        Assert.assertEquals(choosePanelPage.getReportsPanel(), Panel.getReportsPanel(), PanelType.REPORTS.getErrorMessage());
        Assert.assertEquals(choosePanelPage.getHeatMapsPanel(), Panel.getHeatMapsPanel(), PanelType.HEAT_MAPS.getErrorMessage());
    }
}
