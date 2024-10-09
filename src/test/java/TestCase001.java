import com.agest.model.tiki.LeftMenuItem;
import com.agest.page.tiki.FilterModalPage;
import com.agest.page.tiki.HomePage;
import com.agest.page.tiki.ViewProductPage;
import com.agest.utils.Constants;
import com.agest.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class TestCase001 extends TestBase {
    private final HomePage homePage = new HomePage();
    private final ViewProductPage viewProductPage = new ViewProductPage();
    private final FilterModalPage filterModalPage = new FilterModalPage();

    @Test(description = " Verify viewed item displays in the 'Sản phẩm đã xem' section")
    public void testViewedItemDisplaysInTheViewedItemSection() {
        open(Constants.TIKI_URL);
        homePage.closeAdsIfDisplayed();
        homePage.selectLeftMenuItem(LeftMenuItem.NHA_SACH_TIKI);

        viewProductPage.shouldViewProductPageDisplayed(LeftMenuItem.NHA_SACH_TIKI);
        String actualBreadcrumb = viewProductPage.getBreadcrumb();
        String expectedBreadcrumb = "Trang chủ > Nhà Sách Tiki";
        Assert.assertEquals(actualBreadcrumb, expectedBreadcrumb,
                Utils.getErrorMessage(actualBreadcrumb, expectedBreadcrumb));

        viewProductPage.clickFilterAll();
        viewProductPage.shouldFilterAllDialogDisplayed();

        String filterType = "Nhà cung cấp";
        String filterOption = "Nhà sách Fahasa";
        filterModalPage.selectFilterCheckBox(filterType, filterOption);

        String startPrice = "60000";
        String endPrice = "140000";
        filterModalPage.enterPriceRange(startPrice, endPrice);
        filterModalPage.clickFindResult();

        viewProductPage.shouldViewProductPageDisplayed(LeftMenuItem.NHA_SACH_TIKI);
        viewProductPage.shouldPriceOfAllDisplayedItemsWithinRange(startPrice, endPrice);
    }
}
