package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.junit.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;

public class EventsCreateTests {

    static Controller controller = new Controller();
    private static EventsPage eventsPage = new EventsPage();
    private static MainPage mainPage = new MainPage();
    private static TracksPage tracksPage = new TracksPage();
    private static SettingsPage settingsPage = new SettingsPage();
    private static String tracksURL = "https://stage.rcgtiming.com/Timing/Tracks";
    private static String eventsURL = "https://stage.rcgtiming.com/Timing/Competitions";
    private String eventName = "";
    private static String settingsURL = "https://stage.rcgtiming.com/Settings";

    private static String userName = RandomStringUtils.randomAlphabetic(10);
    private static String userEmail;
    private static String password = "123456789a";
    private static String trackTitle = "";
    boolean eventCreated = false;
    static boolean trackCreated = false;

    @BeforeClass
    public static void userCreation() throws InterruptedException, MessagingException, IOException {
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

        trackCreate();
    }

    @Before
    public void openEventsPage() {
        Selenide.open(eventsURL);
        eventName = "Main test event";
    }

    @After
    public void clearData() {

        if (eventCreated) {
            Selenide.open(eventsURL);
            eventsPage.getButtonDeleteEvent(eventName).scrollTo().click();
            eventsPage.getCheckConfirmationEventDelete().click();
            eventsPage.getButtonConfirmEventDelete().click();
            eventCreated = false;
        }
    }

    @AfterClass
    public static void userDeletion() throws InterruptedException {
        if (trackCreated) {
            Selenide.open(tracksURL);
            tracksPage.getButtonDeleteTrack().click();
            trackCreated = false;
            Thread.sleep(3000);
        }
        Selenide.open("https://stage.rcgtiming.com/");
        controller.userLogOut();
        controller.deleteUser();
    }

    @Test
    public void theNameOfCreatedEventShouldBeMainTestEvent() {
        eventsPage.getButtonCreate().click();
        eventsPage.getEventTitleField().setValue(eventName);
        saveEvent(eventName);
        Assert.assertEquals(eventName, eventsPage.getEventTitleInTheTable().getText());
    }

    @Test
    public void shouldBeOpenEyeInEventsTableIfPublicEventTurnOn() {
        eventsPage.getButtonCreate().click();
        eventsPage.getEventTitleField().setValue(eventName);
        eventsPage.getCheckPublicEvent().shouldHave(Condition.checked);
        saveEvent(eventName);
        String actualDataOriginalTitle = eventsPage.getEye().getAttribute("data-original-title");
        Assert.assertEquals("Visible to public", actualDataOriginalTitle);
    }

    @Test
    public void shouldBeOpenEyeSlashInEventsTableIfPublicEventTurnOff() {
        eventsPage.getButtonCreate().click();
        eventsPage.getEventTitleField().setValue(eventName);
        eventsPage.getCheckPublicEvent().shouldHave(Condition.checked).click();
        saveEvent(eventName);
        String actualDataOriginalTitle = eventsPage.getEye().getAttribute("data-original-title");
        Assert.assertEquals("Hidden from the public", actualDataOriginalTitle);
    }

    @Test
    public void shouldBeDisplayStartDateTwoDaysAfterTheCurrentOneOnTheEventsTable() throws InterruptedException {
        String startDate = daysToEventStartOrEnd(2);
        String endDate = daysToEventStartOrEnd(4);
        createEventIncludingDates(eventName, startDate, endDate);
        saveEvent(eventName);
        String actualStartDateOnEventTable = eventsPage.getEventStartDateInTheTable().getText().substring(0, 10);
        if (controller.headless) {
            Assert.assertEquals(startDate, changeDateFormat(actualStartDateOnEventTable));
        } else {
            Assert.assertEquals(startDate, actualStartDateOnEventTable);
        }
    }

    @Test
    public void shouldBeDisplayEndDateFourDaysAfterTheCurrentOneOnTheEventsTable() {
        String startDate = daysToEventStartOrEnd(2);
        String endDate = daysToEventStartOrEnd(4);
        createEventIncludingDates(eventName, startDate, endDate);
        saveEvent(eventName);
        String actualEndDateOnEventTable = eventsPage.getEventEndDateInTheTable().getText();
        if (controller.headless) {
            Assert.assertEquals(endDate.substring(0, 5), changeDateFormat(actualEndDateOnEventTable));
        } else {
            Assert.assertEquals(endDate.substring(0, 5), actualEndDateOnEventTable);
        }
    }

    @Test
    public void shouldReturnErrorEndDateShouldNotGoBeforeStartDateIfEndDateBeforeStartDate() throws InterruptedException {
        String startDate = daysToEventStartOrEnd(4);
        String endDate = daysToEventStartOrEnd(2);
        createEventIncludingDates(eventName, startDate, endDate);
        eventsPage.getButtonSave().click();
        MatcherAssert.assertThat(eventsPage.getDatesError().getText(), containsString("End Date shouldn't go before Start Date"));
    }

    @Test
    public void theNameOfEventOwnerShouldBeTheSameAsTheAccountOwnerName() {
        eventsPage.getButtonCreate().click();
        eventsPage.getEventTitleField().setValue(eventName);
        saveEvent(eventName);
        Assert.assertEquals(userName, eventsPage.getEventOwnerInTheTable().getText());
    }

    @Test
    public void shouldReturnErrorTitleCouldNotBeEmptyIfTheTitleFieldIsEmpty() {
        eventsPage.getButtonCreate().click();
        eventsPage.getButtonSave().click();
        Assert.assertEquals("Title couldn't be empty", eventsPage.getTitleEmptyError().getText());
    }

    @Test
    public void shouldDisplayedTrackNameIfTrackNameWasSelectedInEventForm() {
        eventsPage.getButtonCreate().click();
        eventsPage.getEventTitleField().setValue(eventName);
        eventsPage.getEventTrackField().click();
        eventsPage.getTrack().click();
        saveEvent(eventName);
        Assert.assertEquals(trackTitle, eventsPage.getEventTrackTitleInTheTable().getText());
    }

    public void saveEvent(String title) {
        eventsPage.getButtonSave().click();
        eventsPage.setParameters(title);
        eventCreated = true;
    }

    public String daysToEventStartOrEnd (int daysCount) {
        DateFormat dateFormat;
        if (controller.headless) {
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        } else {
            dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        }
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, daysCount);
        return dateFormat.format(c.getTime());
    }

    public void createEventIncludingDates(String title, String start, String end) {
        eventsPage.getButtonCreate().click();
        eventsPage.getEventTitleField().setValue(title);
        eventsPage.getEventStartDateOnTheForm().setValue(start);
        eventsPage.getEventEndDateOnTheForm().setValue(end);

    }

    public static void trackCreate() throws InterruptedException {
        Selenide.open(settingsURL);
        tracksPage.setCountry(settingsPage.getCountryName().getText());
        Selenide.open(tracksURL);
        tracksPage.getButtonAdd().click();
        trackTitle = userName + "'s track";
        tracksPage.getTrackTitle().setValue(trackTitle);
        tracksPage.getSelectTrackCountry().click();
        tracksPage.getCountry().click();
        tracksPage.getButtonConfirm().click();
        trackCreated = true;
        Thread.sleep(2000);
    }

    public String changeDateFormat(String date) {
        String day = date.substring(0,2);
        String month = date.substring(3,5);
        String newDate = date.replace(".", "/").replace(day + "/" + month, month + "/" + day);
        return newDate;
    }

}
