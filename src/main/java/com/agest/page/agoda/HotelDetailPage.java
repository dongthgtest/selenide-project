package com.agest.page.agoda;

import com.agest.constant.agoda.Facility;
import com.agest.constant.agoda.Review;
import com.agest.page.IPage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class HotelDetailPage implements IPage {
    private SelenideElement hotelNameLabel = $("h1[data-selenium=hotel-header-name]");
    private SelenideElement hotelAddressLabel = $("[data-selenium=hotel-address-map]");
    private ElementsCollection facilityLabels = $$("#abouthotel-features div[data-element-name=property-feature] span");
    private SelenideElement reviewTab = $("#hotelNavBar [data-element-name=customer-reviews-panel-navbar-menu]");
    private SelenideElement reviewSection = $(".Review-traveler-Cell");

    public void hotelNameShouldBe(String expectedHotelName) {
        hotelNameLabel.shouldHave(exactText(expectedHotelName));
    }

    public void hotelAddressShouldContain(String expectedAddress) {
        hotelAddressLabel.shouldHave(text(expectedAddress));
    }

    public void facilityShouldContain(Facility expectedFacility) {
        facilityLabels.should(
                anyMatch("Facility should be present",
                        facility -> facility.getText().contains(i18n.t("agoda.facility." + expectedFacility.name())))
        );
    }

    public void verifyReviewScores(List<Pair<Review, Float>> reviews) {
        clickReviewTab();
        SelenideElement reviewSection = $$("[data-element-name=review-score-details]")
                .shouldHave(sizeLessThanOrEqual(2))
                .get(0);
        for (Pair<Review, Float> review : reviews) {
           reviewSection
                    .$$x(".//span")
                    .findBy(text(i18n.t("agoda.review." + review.getLeft().name())))
                    .should(exist)
                    .sibling(0)
                    .shouldHave(text(String.valueOf(review.getRight())));
        }
    }

    private void clickReviewTab() {
        reviewTab.click();
    }
}
