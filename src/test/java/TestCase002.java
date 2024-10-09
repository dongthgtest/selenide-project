import com.agest.model.tiki.LeftMenuItem;
import com.agest.page.tiki.HomePage;
import com.agest.page.tiki.ProductDetailPage;
import com.agest.page.tiki.ViewProductPage;
import com.agest.utils.Constants;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class TestCase002 extends TestBase {
    private final HomePage homePage = new HomePage();
    private final ViewProductPage viewProductPage = new ViewProductPage();
    private final ProductDetailPage productDetailPage = new ProductDetailPage();

    @Test(description = " Verify viewed item displays in the 'Sản phẩm đã xem' section")
    public void testViewedItemDisplaysInTheViewedItemSection() {
        open(Constants.TIKI_URL);
        homePage.closeAdsIfDisplayed();
        homePage.selectLeftMenuItem(LeftMenuItem.THE_THAO_DA_NGOAI);

        viewProductPage.shouldViewProductPageDisplayed(LeftMenuItem.THE_THAO_DA_NGOAI);
        viewProductPage.selectFirstProduct();

        productDetailPage.shouldDisplayed();
        String itemName = productDetailPage.getItemName();
        productDetailPage.goToHomePage();

        homePage.shouldDisplayed();
        homePage.closeAdsIfDisplayed();
        LeftMenuItem randomItem = LeftMenuItem.getRandomLeftMenuItem();
        homePage.selectLeftMenuItem(randomItem);

        viewProductPage.shouldViewProductPageDisplayed(randomItem);
        viewProductPage.shouldViewedItemDisplayInViewRecentlySection(itemName);
    }
}
