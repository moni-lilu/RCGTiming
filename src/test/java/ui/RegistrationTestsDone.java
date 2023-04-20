package ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.junit.*;


import static org.hamcrest.CoreMatchers.containsString;

public class RegistrationTestsDone {

    static Controller controller;
    public static String urlMainPage = "";
    public static String urlSignUpPage = "";
    private String name = "Paulo";
    private String userEmail;
    private int countryNumber;
    private String password = "123456789a";
    private MainPage mainPage = new MainPage();
    private CreateAccountPage createAccountPage = new CreateAccountPage();
    Boolean userCreated = false;

    static {
        try {
            controller = new Controller();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public static void preparation() {
        urlMainPage = controller.getUrl();
        urlSignUpPage = controller.getUrl() + "signup";
    }

    @Before
    public void createRegistrationData() {
        controller.createRegistrationData();
        userEmail = controller.userEmail;
        countryNumber = controller.countryNumber;
    }

    @After
    public void deleteCreatedUser() throws InterruptedException {
        if (userCreated) {
            controller.deleteUser();
            userCreated = false;
        }
    }

    @Test
    public void shouldOpenSignUpPageFromPleaseSignInModalWindow() {

        Selenide.open(urlMainPage);
        mainPage.getButtonSignIn().click();
        mainPage.getHrefSignUpInSignInWindow().click();
        Assert.assertEquals("https://stage.rcgtiming.com/signup", WebDriverRunner.currentFrameUrl());

    }

    @Test
    public void shouldOpenSignUpPageFromPasswordRecoveryModalWindow() {
        Selenide.open(urlMainPage);
        mainPage.getButtonSignIn().click();
        mainPage.getHrefForgotPasswordInSignInWindow().click();
        mainPage.getHrefSignUpInPasswordRecoveryWindow().click();
        Assert.assertEquals("https://stage.rcgtiming.com/signup", WebDriverRunner.currentFrameUrl());
    }

    @Test
    public void shouldMadeUserRegistrationWithFreePlan() {
        controller.fullShortRegistrationForm("Test-one", userEmail, countryNumber, 0, password, password);
        checkUserCreation();
    }

    @Test
    public void shouldMadeUserRegistrationWithClubPlan() {
        controller.fullShortRegistrationForm("Test-one", controller.userEmail, controller.countryNumber, 1, password, password);
        checkUserCreation();
    }

    @Test
    public void shouldMadeUserRegistrationWithNationalPlan() {
        controller.fullShortRegistrationForm("Test-one", userEmail, countryNumber, 2, password, password);
        checkUserCreation();
    }

    @Test
    public void shouldReturnErrorNameCantBeEmptyIfFieldNameIsEmpty () {
        controller.fullShortRegistrationForm("", userEmail, countryNumber, 2, password, password);
        MatcherAssert.assertThat(createAccountPage.getErrorNameCantBeEmpty().getText(), containsString("Name can't be empty"));
    }

    @Test
    public void shouldReturnCongratulationsIfNameContainsPoint () {
        controller.fullShortRegistrationForm("Mr.Juan", userEmail, countryNumber, 2, password, password);
        checkUserCreation();
    }

    @Test
    public void shouldReturnCongratulationsIfNameContainsHyphen () {
        controller.fullShortRegistrationForm("Juan-Carlos", userEmail, countryNumber, 2, password, password);
        checkUserCreation();
    }

    @Test
    public void shouldReturnCongratulationsIfNameContainsSpase () {
        controller.fullShortRegistrationForm("Juan Carlos", userEmail, countryNumber, 2, password, password);
        checkUserCreation();
    }

    @Test
    public void shouldReturnCongratulationsIfNameContainsApostrophe () {
        controller.fullShortRegistrationForm("Juan's", userEmail, countryNumber, 2, password, password);
        checkUserCreation();
    }

    @Test
    public void shouldReturnErrorNameShouldNotContainSpecialCharactersAndDigitsIfNameContainsDigits () {
        controller.fullShortRegistrationForm("Juan1", userEmail, countryNumber, 2, password, password);
        MatcherAssert.assertThat(createAccountPage.getErrorNameShouldNotContainSpecialSymbols().getText(), containsString("Name should not contain special characters and digits"));
    }
    //спецсимволы
    @Test
    public void shouldReturnErrorNameShouldNotContainSpecialCharactersAndDigitsIfNameContainsOneOfTheSpecialSymbols () {
        String[] symbols = {"!", "@", "#", "$", "%", "^", "&", "*", "№", ";", ":", "?", "(", ")", "+", "_", "/", "{", "}", "[", "]", "<", ">"};
        for (int i = 0; i < symbols.length; i++) {
            controller.fullShortRegistrationForm("Juan" + symbols[i], userEmail, countryNumber, 2, password, "");
            MatcherAssert.assertThat(createAccountPage.getErrorNameShouldNotContainSpecialSymbols().getText(), containsString("Name should not contain special characters and digits"));
        }
    }

    @Test
    public void shouldTake50SymbolsName() {
        controller.fullShortRegistrationForm(generateString(50),
                userEmail,
                countryNumber,
                2,
                password,
                password);
        checkUserCreation();
    }

    @Test
    public void shouldNotTake51SymbolsName() {
        controller.fullShortRegistrationForm(generateString(51),
                userEmail,
                countryNumber,
                2,
                password,
                password);
        MatcherAssert.assertThat(createAccountPage.getErrorNameShouldBeFiftyCharactersLongAtMost().getText(), containsString("Name should be 50 characters long at most"));
    }

    @Test
    public void shouldReturnErrorEmailCantBeEmptyIfFieldNameIsEmpty () {
        controller.fullShortRegistrationForm(name, "", countryNumber, 2, password, password);
        MatcherAssert.assertThat(createAccountPage.getErrorEmailCantBeEmpty().getText(), containsString("Email can't be empty"));
    }

    @Test
    public void shouldLabelPasswordClassHasError () {

        controller.fullShortRegistrationForm(name, userEmail, countryNumber, 2, "", password);
        MatcherAssert.assertThat(createAccountPage.getErrorEmptyPassword().getAttribute("class"), containsString("form-group has-error"));

    }

    @Test
    public void shouldLabelConfirmPasswordClassHasError () {

        controller.fullShortRegistrationForm(name, userEmail, countryNumber, 2, password, "");
        MatcherAssert.assertThat(createAccountPage.getErrorEmptyConfirmPassword().getAttribute("class"), containsString("form-group has-error"));

    }

    @Test
    public void shouldReturnErrorInvalidEmailAddressIfEmailAddressDoNotContainPointInRightPart () {

        controller.fullShortRegistrationForm(name, "test@gmailcom", countryNumber, 2, password, password);
        MatcherAssert.assertThat(createAccountPage.getErrorInvalidEmailAddress().getText(), containsString("Invalid Email Address"));
    }

    @Test
    public void shouldSignUpWithEmailAddressWhichIncludingDigits () {

        controller.fullShortRegistrationForm(name, "test123@gmail.com", countryNumber, 2, password, password);
        checkUserCreation();

    }

    @Test
    public void shouldSignUpWithEmailAddressWhichIncludingHyphen () {

        controller.fullShortRegistrationForm(name, "test-test@gmail.com", countryNumber, 2, password, password);
        checkUserCreation();

    }

    @Test
    public void shouldSignUpWithEmailAddressWhichIncludingPlus () {

        controller.fullShortRegistrationForm(name, "test+test@gmail.com", countryNumber, 2, password, password);
        checkUserCreation();

    }

    @Test
    public void shouldSignUpWithEmailAddressWhichIncludingPointInLeftPart () {

        controller.fullShortRegistrationForm(name, "test.test@gmail.com", countryNumber, 2, password, password);
        checkUserCreation();

    }

    @Test
    public void shouldSignUpWithEmailAddressWhichIncludingUnderscore () {

        controller.fullShortRegistrationForm(name, "test_test@gmail.com", countryNumber, 2, password, password);
        checkUserCreation();

    }

    /*  @Test
    public void shouldReturnErrorInvalidEmailAddressIfEmailAddressContainsOneOfTheSpecialSymbols () {

        createRegistrationData();

        String[] symbols = {"!", "'", "#", "$", "%", "^", "&", "*", "№", ";", ":", "?", "(", ")", " ", "/", "{", "}", "[", "]", "<", ">"};
        for (int i = 0; i < symbols.length; i++) {
            fullTheRegistrationForm(name, "test" + symbols[i] + "test@gmail.com", countryNumber, 2, password, password);
            MatcherAssert.assertThat(createAccountPage.getErrorInvalidEmailAddress().getText(), containsString("Invalid Email Address"));
        }

    }*/

    @Test
    public void shouldSignUpWithFiveSymbolsEmailAddress () {

        controller.fullShortRegistrationForm(name, "t@g.c", countryNumber, 2, password, password);
        checkUserCreation();

    }

    @Test
    public void shouldSignUpWith58SymbolsEmailAddress () {

        controller.fullShortRegistrationForm(name,
                generateString(48) + "@gmail.com",
                countryNumber,
                2,
                password,
                password);
        checkUserCreation();

    }

    @Test
    public void shouldSignUpWith99SymbolsEmailAddress () {

        controller.fullShortRegistrationForm(name,
                generateString(89) + "@gmail.com",
                countryNumber,
                2,
                password,
                password);
        checkUserCreation();
    }

    @Test
    public void shouldSignUpWith100SymbolsEmailAddress () {

        controller.fullShortRegistrationForm(name,
                generateString(90) + "@gmail.com",
                countryNumber,
                2,
                password,
                password);
        checkUserCreation();

    }

    @Test
    public void shouldReturnErrorEmailAddressLengthFor101SymbolsEmailAddress () {
        controller.fullShortRegistrationForm(name,
                generateString(91) + "@gmail.com",
                countryNumber,
                2,
                password,
                password);
        MatcherAssert.assertThat(createAccountPage.getErrorEmailAddressLength().getText(), containsString("Email should be 100 characters long at most"));
    }

    @Test
    public void shouldReturnErrorEmailAddressLengthFor154SymbolsEmailAddress () {
        controller.fullShortRegistrationForm(name,
                generateString(144) + "@gmail.com",
                countryNumber,
                2,
                password,
                password);
        MatcherAssert.assertThat(createAccountPage.getErrorEmailAddressLength().getText(), containsString("Email should be 100 characters long at most"));
    }

    @Test
    public void shouldReturnErrorPasswordShouldBeTenCharactersAtLeastForFourSymbolsPassword() {
        controller.fullShortRegistrationForm(name, userEmail, countryNumber, 2, "123a", "123a");
        MatcherAssert.assertThat(createAccountPage.getErrorPasswordShouldBeTenCharactersAtLeast().getText(), containsString("Password should be at least 10 characters long"));
    }

    @Test
    public void shouldReturnErrorPasswordShouldBeTenCharactersAtLeastForNineSymbolsPassword() {
        controller.fullShortRegistrationForm(name,
                userEmail,
                countryNumber,
                2,
                "12345678a",
                "12345678a");
        MatcherAssert.assertThat(createAccountPage.getErrorPasswordShouldBeTenCharactersAtLeast().getText(), containsString("Password should be at least 10 characters long"));
    }

    @Test
    public void shouldSignUpWithTenSymbolsPassword () {
        controller.fullShortRegistrationForm(name,
                userEmail,
                countryNumber,
                2,
                "123456789a",
                "123456789a");
        checkUserCreation();
    }

    @Test
    public void shouldSignUpWithElevenSymbolsPassword () {
        controller.fullShortRegistrationForm(name,
                userEmail,
                countryNumber,
                2,
                "1234567890a",
                "1234567890a");
        checkUserCreation();
    }

    @Test
    public void shouldSignUpWithTwentyOneSymbolsPassword () {
        controller.fullShortRegistrationForm(name,
                userEmail,
                countryNumber,
                2,
                "12345678901234567890a",
                "12345678901234567890a");
        checkUserCreation();
    }

    @Test
    public void shouldReturnErrorPasswordShouldMatchIfPasswordsNotMatch() {
        controller.fullShortRegistrationForm(name,
                userEmail,
                countryNumber,
                2,
                "123456789a",
                "a987654321");
        MatcherAssert.assertThat(createAccountPage.getErrorPasswordShouldMatch().getText(), containsString("Passwords should match"));
    }

    @Test
    public void shouldSignUpIfRequiredFieldsAndFieldLastNameAreCompleted () {
        controller.fullAllRegistrationForm(name,
                "Smith",
                userEmail,
                countryNumber,
                2,
                "",
                5,
                "",
                "",
                password,
                password);
        checkUserCreation();
    }

    @Test
    public void shouldSignUpIfRequiredFieldsAndFieldTrackNameAreCompleted () {
        controller.fullAllRegistrationForm(name,
                "",
                userEmail,
                countryNumber,
                2,
                "MyTrack",
                5,
                "",
                "",
                password,
                password);
        checkUserCreation();
    }

    @Test
    public void shouldSignUpIfRequiredFieldsAndFieldTypeAreCompleted () {
        controller.fullAllRegistrationForm(name,
                "",
                userEmail,
                countryNumber,
                2,
                "",
                2,
                "",
                "",
                password,
                password);
        checkUserCreation();
    }

    @Test
    public void shouldSignUpIfRequiredFieldsAndFieldCityAreCompleted () {
        controller.fullAllRegistrationForm(name,
                "",
                userEmail,
                countryNumber,
                2,
                "",
                5,
                "London",
                "",
                password,
                password);
        checkUserCreation();
    }

    @Test
    public void shouldSignUpIfRequiredFieldsAndFieldAddressAreCompleted () {
        controller.fullAllRegistrationForm(name,
                "",
                userEmail,
                countryNumber,
                2,
                "",
                5,
                "",
                "Baker Street, 221b",
                password,
                password);
        checkUserCreation();
        System.out.println("userCreated: " + userCreated);
    }

    @Test
    public void shouldReturnErrorUserWithThisEmailAlreadyRegisteredIfUserWithSuchEmailHasAlreadyExist() {
        controller.createRegistrationData();
        controller.fullShortRegistrationForm(name, userEmail, countryNumber, 1, password, password);
        Selenide.open(urlSignUpPage);
        String sameEmail = userEmail;
        controller.fullShortRegistrationForm(name, sameEmail, countryNumber, 1, password, password);
        MatcherAssert.assertThat(createAccountPage.getErrorUserWithThisEmailAlreadyRegistered().getText(), containsString("User with this Email already registered"));
    }

    public void checkUserCreation() {
        LoginSuccessPage loginSuccessPage = new LoginSuccessPage();
        MatcherAssert.assertThat(loginSuccessPage.getTextCongratulations().getText(), containsString("Congratulations!"));
        userCreated = true;
    }

    public String generateString(int length) {

        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        return generatedString;
    }

}
