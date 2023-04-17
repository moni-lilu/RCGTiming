package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.mail.MessagingException;
import java.io.IOException;

import static com.codeborne.selenide.WebDriverRunner.url;

public class CarRaceStartingAndHoldingTest {
    private static final String eventsURL = "https://stage.rcgtiming.com/Timing/Competitions";
    private static final String mainURL = "https://stage.rcgtiming.com/";
    static Controller controller = new Controller();
    private static MainPage mainPage = new MainPage();
    private static EventPage eventPage = new EventPage();
    private static CompetitionHeatPage competitionHeatPage = new CompetitionHeatPage();
    private static String userName = "Robert Buttle";
    private static String userEmail;
    private static String password = "123456789a";
    private static String eventName = "Ester bunny";
    private static String event = "";
    private static int waitForSeconds = 11000;

    @BeforeClass
    public static void userAndEventCreation() throws InterruptedException, MessagingException, IOException {

        controller.createRegistrationData();
        userEmail = controller.userEmail;
        controller.fullShortRegistrationForm(
                userName,
                userEmail,
                controller.countryNumber,
                1,
                password,
                password
        );
        controller.activateAccount();
// для отладки
    /*    Selenide.open("https://rcgt:forthewin@stage.rcgtiming.com/");
        controller.userSignIn("july.luna.m+maintest@gmail.com", password);
        mainPage.getEventsButton().click();*/
// -----------
        controller.eventCreation(eventName);
        controller.eventsPage.getEventTitleInTheTable().click();
        event = url() + "#drivers";
        eventPage.getClassesTab().click();
        eventPage.getAddClassesButton().click();
        eventPage.getBuggy8Nitro().click();
        eventPage.getConfirmButtonInAddClassesModalWindow().click();
        eventPage.getDriversTab().click();
        DriverRegistrationTests.addDriverEntry("Buggy 8 Nitro",
                "Carlos",
                "Canas",
                "Spain",
                "7711177");
        eventPage.getScheduleTab().click();
    }

    @Test
    public void lapTimeAndSleepTimeShouldBeTheSame() throws InterruptedException {
        eventPage.getButtonRace().click();
        eventPage.getSelectGroup().selectOption(1);
        eventPage.getInputCountdown().clear();
        eventPage.getButtonConfirmInRaceSettingsModalWindow().click();
        competitionHeatPage.getButtonHeatDetails().click();
        competitionHeatPage.getButtonAddDriver().click();
        competitionHeatPage.getGreenButtonAtHeatDetailsModalWindow().click();
        competitionHeatPage.getButtonOk().click();
        competitionHeatPage.getButtonStartCountdown().click();
        Thread.sleep(500);
        competitionHeatPage.getButtonManualLapsInput().click();
        competitionHeatPage.getButtonPointer().click();
        Thread.sleep(waitForSeconds);
        competitionHeatPage.getButtonPointer().click();
        Thread.sleep(3000);
        String actualTime = competitionHeatPage.getLastLap().getText();
        Double actual = Double.valueOf(actualTime);
        competitionHeatPage.getButtonRaceFinish().click();
        competitionHeatPage.getButtonConfirm().click();
        Assert.assertEquals(waitForSeconds/1000, actual.intValue());
    }

    @AfterClass
    public static void userAndEventDeletion() throws InterruptedException {
        Selenide.open(eventsURL);
        controller.eventDelete();
        Selenide.open(mainURL);
        controller.userLogOut();
        controller.deleteUser();
    }
}
