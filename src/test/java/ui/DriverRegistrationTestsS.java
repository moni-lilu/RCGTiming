package ui;

import com.codeborne.selenide.Selenide;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;

import javax.mail.MessagingException;
import java.io.IOException;

import static com.codeborne.selenide.WebDriverRunner.url;

public class DriverRegistrationTestsS {

    static Controller controller = new Controller();
    private static MainPage mainPage = new MainPage();
    private static EventsPage eventsPage = new EventsPage();
    private static String eventsURL = "https://stage.rcgtiming.com/Timing/Competitions";
    private static EventPage eventPage = new EventPage();
    private static String userName = RandomStringUtils.randomAlphabetic(10);
    private static String userEmail;
    private static String password = "123456789a";
    private static String eventName = "Odessa cup";
    private static String event = "";

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
 /*       Selenide.open("https://rcgt:forthewin@stage.rcgtiming.com/");
        controller.userSignIn("july.luna.m+maintest@gmail.com", password);
        mainPage.getEventsButton().click();*/
// --------------------
        controller.eventCreation(eventName);
        controller.eventsPage.getEventTitleInTheTable().click();
        event = url() + "#drivers";
        addClasses();
        eventPage.getDriversTab().click();

    }

    @After
    public void refreshPage() {
        Selenide.open(event);
    }

    @AfterClass
    public static void userAndEventDeletion() throws InterruptedException {
        controller.eventDelete();
        Selenide.open("https://stage.rcgtiming.com/");
        controller.userLogOut();
        controller.deleteUser();
    }

    @Test
    public void shouldDisplayFirstNameAndLastNameSuchAsWasEnteredInRegistrationForm() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Carlos",
                "Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getNameInTable().getText();
        deleteEntry();
        Assert.assertEquals("Juan-Carlos Canas", actual);
    }

    @Test
    public void shouldDisplayClassNameSuchAsWasEnteredInRegistrationForm() throws InterruptedException {
        addDriverEntry("Buggy 2wd",
                "Carlos",
                "Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getClassNameInTable().getText();
        deleteEntry();
        Assert.assertEquals("Buggy 2wd", actual);
    }

    @Test
    public void shouldDisplayTransponderSuchAsWasEnteredInRegistrationForm() throws InterruptedException {
        addDriverEntry("Buggy 2wd",
                "Carlos",
                "Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getTransponderNumberInTable().getText();
        deleteEntry();
        Assert.assertEquals("7711177", actual);
    }

    @Test
    public void shouldDisplayCountrySuchAsWasEnteredInRegistrationForm() throws InterruptedException {
        addDriverEntry("Buggy 2wd",
                "Carlos",
                "Canas",
                "Cuba",
                "7711177");
        displayingCountry(true);
        Thread.sleep(1000);
        String actual = eventPage.getCountryInTable().getText();
        displayingCountry(false);
        deleteEntry();
        Assert.assertEquals("Cuba", actual);
    }

    @Test
    public void returnErrorNameCantBeEmptyIfFieldFirstNameIsEmpty() throws InterruptedException {
        addDriverEntry("Buggy 2wd",
                "",
                "Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getModalWindowError().getText();
        Assert.assertEquals("First and Last Name should not be empty", actual);
    }

    @Test
    public void returnErrorNameCantBeEmptyIfFieldLastNameIsEmpty() throws InterruptedException {
        addDriverEntry("Buggy 2wd",
                "Carlos",
                "",
                "Cuba",
                "7711177");
        String actual = eventPage.getModalWindowError().getText();
        Assert.assertEquals("First and Last Name should not be empty", actual);
    }

    @Test
    public void shouldBeBlockedDriverRegistrationFormIfClassWasNotSelected() {
        eventPage.getAddDriverEntryButton().click();
        String actual = eventPage.getDriverRegistrationForm().getAttribute("style");
        Assert.assertEquals("", actual);
    }

    //!!!!!!!!!
    @Test
    public void shouldReturnErrorInvalidOrEmptySectionIfClassWasNotSelectedAndSaveWasPressed() {
        eventPage.getAddDriverEntryButton().click();
        eventPage.getButtonSaveInModalWindow().click();
        String actual = eventPage.getModalWindowError().getText();
        Assert.assertEquals("Invalid or empty Section", actual);
    }
    @Test
    public void registrationMustBeSuccessfulIfNameContainsPoint() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Mr.Juan",
                "Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getNameInTable().getText();
        deleteEntry();
        Assert.assertEquals("Mr.Juan Canas", actual);
    }
    @Test
    public void registrationMustBeSuccessfulIfNameContainsHyphen() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan-Carlos",
                "Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getNameInTable().getText();
        deleteEntry();
        Assert.assertEquals("Juan-Carlos Canas", actual);
    }
    @Test
    public void registrationMustBeSuccessfulIfNameContainsSpase() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getNameInTable().getText();
        deleteEntry();
        Assert.assertEquals("Juan Carlos Canas", actual);
    }
    @Test
    public void registrationMustBeSuccessfulIfNameContainsApostrophe() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan's",
                "Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getNameInTable().getText();
        deleteEntry();
        Assert.assertEquals("Juan's Canas", actual);
    }

    @Test
    public void registrationMustBeSuccessfulIfLastNameContainsPoint() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan",
                "Mr.Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getNameInTable().getText();
        deleteEntry();
        Assert.assertEquals("Juan Mr.Canas", actual);
    }
    @Test
    public void registrationMustBeSuccessfulIfLastNameContainsHyphen() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Carlos",
                "Juan-Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getNameInTable().getText();
        deleteEntry();
        Assert.assertEquals("Carlos Juan-Canas", actual);
    }
    @Test
    public void registrationMustBeSuccessfulIfLastNameContainsSpase() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Carlos",
                "Juan Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getNameInTable().getText();
        deleteEntry();
        Assert.assertEquals("Carlos Juan Canas", actual);
    }
    @Test
    public void registrationMustBeSuccessfulIfLastNameContainsApostrophe() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan",
                "Canas's",
                "Cuba",
                "7711177");
        String actual = eventPage.getNameInTable().getText();
        deleteEntry();
        Assert.assertEquals("Juan Canas's", actual);
    }
    @Test
    public void shouldReturnErrorFirstNameShouldNotContainDigitsIfFirstNameContainsDigits() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan1",
                "Canas",
                "Cuba",
                "7711177");
        String actual = eventPage.getModalWindowError().getText();
        eventPage.getCancelButtonInEditForm().click();
        Assert.assertEquals("First Name should not contain digits", actual);
    }
    @Test
    public void shouldReturnErrorLastNameShouldNotContainDigitsIfLastNameContainsDigits() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan",
                "Canas1",
                "Cuba",
                "7711177");
        String actual = eventPage.getModalWindowError().getText();
        eventPage.getCancelButtonInEditForm().click();
        Assert.assertEquals("Last Name should not contain digits", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulIfOneTransponderNumberContainsTenDigits() throws InterruptedException {
        String transponderNumber = "0123456789";
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                transponderNumber);
        String actual = eventPage.getTransponderNumberInTable().getText();
        deleteEntry();
        Assert.assertEquals(transponderNumber, actual);
    }

    @Test
    public void registrationShouldBeSuccessfulIfOneTransponderNumberContainsNineDigits() throws InterruptedException {
        String transponderNumber = "123456789";
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                transponderNumber);
        String actual = eventPage.getTransponderNumberInTable().getText();
        deleteEntry();
        Assert.assertEquals(transponderNumber, actual);
    }

    @Test
    public void registrationShouldBeSuccessfulIfOneTransponderNumberContainsOneDigit() throws InterruptedException {
        String transponderNumber = "1";
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                transponderNumber);
        String actual = eventPage.getTransponderNumberInTable().getText();
        deleteEntry();
        Assert.assertEquals(transponderNumber, actual);
    }

    @Test
    public void shouldDisplayedErrorIfOneTransponderNumberContainsElevenDigit() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "01234567899");
        String actual = eventPage.getInvalidTransponderNumberError().getText();
        eventPage.getCancelButtonInEditForm().click();
        Assert.assertEquals("Invalid Transponder number", actual);
    }

    @Test
    public void shouldDisplayedErrorIfOneTransponderNumberContainsFifteenDigit() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "012345678900000");
        String actual = eventPage.getInvalidTransponderNumberError().getText();
        eventPage.getCancelButtonInEditForm().click();
        Assert.assertEquals("Invalid Transponder number", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulWithTwoTransponders() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "0123456789 1234567890/123");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().click();
        deleteEntry();
        Assert.assertEquals("0123456789 1234567890/123", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulWithThreeTransponders() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "0123456789 1234567890/321 2345678901");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("0123456789 1234567890/321 2345678901", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulWithFourTransponders() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "0123456789 1234567890/321 2345678901 321456/987");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("0123456789 1234567890/321 2345678901 321456/987", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulIfTranspondersSeparatedByCommas() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "0123456789,1234567890/321,2345678901,321456/987");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("0123456789 1234567890/321 2345678901 321456/987", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulIfTranspondersSeparatedByCommasAndSpaces() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "0123456789, 1234567890/321, 2345678901, 321456/987");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("0123456789 1234567890/321 2345678901 321456/987", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulIfTranspondersSeparatedBySemicolon() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "0123456789;1234567890/321;2345678901;321456/987");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("0123456789 1234567890/321 2345678901 321456/987", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulIfTranspondersSeparatedByFiveCommasAndFiveSpaces() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "0123456789,,,,,     1234567890/321,,,,,     2345678901,,,,,     321456/987");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("0123456789 1234567890/321 2345678901 321456/987", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulIfTheBeginningOfTheTransponderStringContainsSpase() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                " 0123456789,1234567890/321,2345678901,321456/987");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("0123456789 1234567890/321 2345678901 321456/987", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulIfTheEndingOfTheTransponderStringContainsSpase() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "0123456789,1234567890/321,2345678901,321456/987  ");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("0123456789 1234567890/321 2345678901 321456/987", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulIfTheEndingOfTheTransponderStringContainsComma() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "0123456789,1234567890/321,2345678901,321456/987,");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("0123456789 1234567890/321 2345678901 321456/987", actual);
    }
//!!!!!!!!!!!
    @Test
    public void registrationShouldBeSuccessfulIfTheStartingOfTheTransponderStringContainsComma() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                ",0123456789,1234567890/321,2345678901,321456/987");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("0123456789 1234567890/321 2345678901 321456/987", actual);
    }

    @Test
    public void shouldReturnErrorTooManyTranspondersForRegistrationWithFiveTransponders() throws InterruptedException {
        String[] transponders = {"0123489,1234590/321,2378901,321456/987,654654",
                "0123489 1234590/321 2378901 321456/987 654654",
                "0123489, 1234590/321, 2378901, 321456/987, 654654"};
        for (int i = 0; i < transponders.length; i++) {
            addDriverEntry("Buggy 8 Nitro",
                    "Juan Carlos",
                    "Canas",
                    "Spain",
                    transponders[i]);
            String actual = eventPage.getErrorTooManyTransponders().getText();
            eventPage.getCancelButtonInEditForm().scrollTo().click();
            Thread.sleep(1000);
            Assert.assertEquals("Too many transponders", actual);
        }
    }

    @Test
    public void shouldReturnInvalidTransponderNumberErrorForNumberWithInvalidSymbols() throws InterruptedException {
        String[] symbols = {"s", "щ", "'", "!", "@", "#", "$", "%", "^", "&", "*", "№", ":", "?", "(", ")", "+", "-", "_", "{", "}", "[", "]", "<", ">"};
        for (int i = 0; i < symbols.length; i++) {
            addDriverEntry("Buggy 8 Nitro",
                    "Juan Carlos",
                    "Canas",
                    "Spain",
                    "123456" + symbols[i]);
            String actual = eventPage.getInvalidTransponderNumberError().getText();
            eventPage.getCancelButtonInEditForm().click();
            Thread.sleep(1000);
            System.out.println(symbols[i]);
            Assert.assertEquals("Invalid Transponder number", actual);
        }
    }

    @Test
    public void shouldReturnErrorWithButtonFixTheNumberIfTwoUsersAreUsingTheSameTwoTransponderNumbers() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "123456");
        Thread.sleep(500);
        addDriverEntry("Buggy 8 Nitro",
                "Antonio",
                "Banderas",
                "Italy",
                "123456");
        String actual = eventPage.getErrorTransponderNumberDuplicate().getText();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("Transponder number duplicate Fix the Number", actual);
    }

    @Test
    public void registrationShouldBeSuccessfulWithOneTransponderNumberIfOneUserUseTheSameTwoTransponderNumbersForRegistration() throws InterruptedException {
        addDriverEntry("Buggy 8 Nitro",
                "Juan Carlos",
                "Canas",
                "Spain",
                "123456 123456");
        eventPage.getNameInTable().click();
        String actual = eventPage.getTransponderFieldInEditForm().getValue();
        eventPage.getCancelButtonInEditForm().scrollTo().click();
        deleteEntry();
        Assert.assertEquals("123456", actual);
    }

/* Добавить проверку на недопустимые символы
    @Test
    public void shouldReturnErrorNameShouldNotContainSpecialCharactersAndDigitsIfNameContainsOneOfTheSpecialSymbols () throws InterruptedException {

        String[] symbols = {"!", "@", "#", "$", "%", "^", "&", "*", "№", ";", ":", "?", "(", ")", "+", "_", "/", "{", "}", "[", "]", "<", ">"};
        for (int i = 0; i < symbols.length; i++) {
            addDriverEntry("Buggy 8 Nitro",
                    "Juan" + symbols[i],
                    "Canas",
                    "Cuba",
                    "7711177");
            MatcherAssert.assertThat(createAccountPage.getErrorNameShouldNotContainSpecialSymbols().getText(), containsString("Name should not contain special characters and digits"));
        }

    }*/

    public static void addClasses() {
        eventPage.getClassesTab().click();
        eventPage.getAddClassesButton().click();
        eventPage.getBuggy2WDCheckbox().click();
        eventPage.getConfirmButtonInAddClassesModalWindow().click();
        eventPage.getAddClassesButton().click();
        eventPage.getBuggy8Nitro().click();
        eventPage.getConfirmButtonInAddClassesModalWindow().click();
    }

    public static void addDriverEntry(String className,
                                               String firstName,
                                               String lastName,
                                               String country,
                                               String transponder) throws InterruptedException {
        eventPage.getAddDriverEntryButton().click();
        Thread.sleep(500);
        eventPage.getClassButtonInModalWindow(className).click();
        String entrySection = eventPage.getClassButtonInModalWindow(className).getAttribute("data-value");
        eventPage.getFirstNameInActiveInputForm(entrySection).setValue(firstName);
        eventPage.getLastNameInActiveInputForm(entrySection).setValue(lastName);
        eventPage.getCountryInActiveInputForm(entrySection).click();
        eventPage.getItemInList(country).click();
        Thread.sleep(3000);
        eventPage.getTransponderInActiveInputForm(entrySection).setValue(transponder);
        eventPage.getButtonSaveInModalWindow().click();
        Thread.sleep(3000);
    }
    public static void deleteEntry() {
        eventPage.getDeleteEntryButton().scrollTo().click();
        Selenide.switchTo().alert().accept();
    }

    public static void displayingCountry(Boolean offOn) {
        eventPage.getAdminDriverListSettingsButton().click();
        eventPage.getCountryCheckboxInListSettings().setSelected(offOn);
        eventPage.getConfirmButtonInListSettings().click();
    }
}
