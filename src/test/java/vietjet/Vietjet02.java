package vietjet;

import com.agest.model.vietjet.SearchFlightCriteria;
import com.agest.page.vietjet.HomePage;
import com.agest.page.vietjet.PassengerInformationPage;
import com.agest.page.vietjet.SelectFlightCheapPage;
import com.agest.page.vietjet.SelectTravelOptionPage;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static com.agest.constant.vietjet.Airport.HA_NOI;
import static com.agest.constant.vietjet.Airport.HO_CHI_MINH;
import static com.agest.constant.vietjet.TicketType.ROUND_TRIP;

public class Vietjet02 extends TestBase {
    LocalDate fromDate = LocalDate.now().plusDays(1);
    LocalDate toDate = fromDate.plusDays(3);
    SearchFlightCriteria criteria;
    HomePage homePage = new HomePage();
    SelectFlightCheapPage selectFlightCheapPage = new SelectFlightCheapPage();
    SelectTravelOptionPage selectTravelOptionPage = new SelectTravelOptionPage();
    PassengerInformationPage passengerInformationPage = new PassengerInformationPage();
    Pair<LocalDate, LocalDate> dateRange;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        criteria = SearchFlightCriteria.builder()
                .departureAirport(HO_CHI_MINH)
                .arrivalAirport(HA_NOI)
                .departureDate(fromDate)
                .returnDate(toDate)
                .ticketType(ROUND_TRIP)
                .findCheapest(true)
                .numberOfAdults(2)
                .build();

        homePage.acceptCookie();

        homePage.closeNotificationPopup();
    }

    @Test(
            description = "Search and choose cheapest tickets on next 3 months successfully",
            groups = {"vietjet", "regression", "smoke"}
    )
    public void testSearchAndChooseCheapestTicketsOnNext3MonthsSuccessfully() {
        homePage.searchFlight(criteria);

        selectFlightCheapPage.verifyPageIsDisplayed();

        selectFlightCheapPage.verifyDepartureAndArrivalPlaces(criteria.getDepartureAirport(), criteria.getArrivalAirport());

        dateRange = selectFlightCheapPage.findCheapestReturnFlight(3);

        // re-assign criteria with the found dates
        criteria.setDepartureDate(dateRange.getLeft());
        criteria.setReturnDate(dateRange.getRight());

        selectFlightCheapPage.selectDepartureFlight(criteria.getDepartureDate());

        selectFlightCheapPage.selectReturnFlight(criteria.getReturnDate());

        selectFlightCheapPage.goToNext();

        selectTravelOptionPage.verifyPageIsDisplayed();

        selectTravelOptionPage.verifyFlightDate(criteria.getDepartureDate());

        selectTravelOptionPage.verifyDepartureAndArrivalPlaces(
                criteria.getDepartureAirport(),
                criteria.getArrivalAirport()
        );

        selectTravelOptionPage.verifyPassengerCount(
                criteria.getNumberOfAdults(),
                criteria.getNumberOfChildren(),
                criteria.getNumberOfInfants()
        );

        selectTravelOptionPage.closeDialog();

        selectTravelOptionPage.closeNotificationPopup();

        selectTravelOptionPage.selectCheapestTicket();

        selectTravelOptionPage.goToNext();

        selectTravelOptionPage.verifyFlightDate(criteria.getReturnDate());

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
