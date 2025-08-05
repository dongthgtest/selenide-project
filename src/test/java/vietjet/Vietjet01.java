package vietjet;

import com.agest.constant.vietjet.Airport;
import com.agest.constant.vietjet.TicketType;
import com.agest.model.vietjet.SearchFlightCriteria;
import com.agest.page.vietjet.HomePage;
import com.agest.page.vietjet.PassengerInformationPage;
import com.agest.page.vietjet.SelectTravelOptionPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class Vietjet01 extends TestBase {
    LocalDate fromDate = LocalDate.now().plusDays(1);
    LocalDate toDate = fromDate.plusDays(3);
    SearchFlightCriteria criteria;
    HomePage homePage = new HomePage();
    SelectTravelOptionPage selectTravelOptionPage = new SelectTravelOptionPage();
    PassengerInformationPage passengerInformationPage = new PassengerInformationPage();

    @BeforeMethod(alwaysRun = true)
    public void preCondition() {
        criteria = SearchFlightCriteria.builder()
                .departureAirport(Airport.HO_CHI_MINH)
                .arrivalAirport(Airport.HA_NOI)
                .departureDate(fromDate)
                .returnDate(toDate)
                .ticketType(TicketType.ROUND_TRIP)
                .numberOfAdults(2)
                .build();

        homePage.acceptCookie();
        homePage.closeNotificationPopup();
    }

    @Test(
            description = "Search and choose tickets on a specific day successfully",
            groups = {"vietjet", "regression", "smoke"}
    )
    public void testSearchAndChooseTicketWorkCorrectlyOnSpecificDay() {
        homePage.searchFlight(criteria);

        selectTravelOptionPage.verifyPageIsDisplayed();

        selectTravelOptionPage.closeDialog();

        selectTravelOptionPage.verifyTicketPriceDisplayedInVND();

        selectTravelOptionPage.verifyFlightDate(fromDate);

        selectTravelOptionPage.verifyDepartureAndArrivalPlaces(
                criteria.getDepartureAirport(),
                criteria.getArrivalAirport()
        );

        selectTravelOptionPage.verifyPassengerCount(
                criteria.getNumberOfAdults(),
                criteria.getNumberOfChildren(),
                criteria.getNumberOfInfants()
        );

        selectTravelOptionPage.selectCheapestTicket();

        selectTravelOptionPage.goToNext();

        selectTravelOptionPage.verifyTicketPriceDisplayedInVND();

        selectTravelOptionPage.verifyFlightDate(toDate);

        // Verify return flight details, so we switch departure and arrival airports
        selectTravelOptionPage.verifyDepartureAndArrivalPlaces(
                criteria.getArrivalAirport(),
                criteria.getDepartureAirport()
        );

        selectTravelOptionPage.verifyPassengerCount(
                criteria.getNumberOfAdults(),
                criteria.getNumberOfChildren(),
                criteria.getNumberOfInfants()
        );

        selectTravelOptionPage.selectCheapestTicket();

        selectTravelOptionPage.goToNext();

        passengerInformationPage.verifyPassengerInformationPageDisplayed();

        passengerInformationPage.verifyTicketInformation(criteria);
    }
}
