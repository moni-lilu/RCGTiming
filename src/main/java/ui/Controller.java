package ui;

import com.codeborne.selenide.Selenide;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.MessagingException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Controller {

    private static final String[] data;

    static {
        try {
            data = getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String urlMainPage = "https://" + data[0] + "@stage.rcgtiming.com/";

    private static final String pathToConfigFile = "C:/Users/Luna/IdeaProjects/RCGT-secret/pass.txt";
    private static final String eventsURL = "https://stage.rcgtiming.com/Timing/Competitions";
    private static final String adminEmail = data[1];
    private static final String adminPass = data[2];
    private static final String testEmailForActivation = data[3];
    private static final String secretPasswordForEmail = data[4];
    private static final String downloadsFolderPath = data[5];
    private static final String testIPForConnection = data[6];
    private static final String authToken = data[7];
    EventsPage eventsPage = new EventsPage();
    int countryNumber;
    String userEmail;
    MainPage mainPage = new MainPage();
    CreateAccountPage createAccountPage = new CreateAccountPage();

    public String getUrl() {
        return urlMainPage;
    }
    public String getAdminEmail() {return adminEmail;}
    public String getAdminPassword() {return adminPass;}
    public String getTestEmailForActivation() {return testEmailForActivation;}
    public String getSecretPasswordForEmail() {return secretPasswordForEmail;}
    public String getDownloadsFolderPath() {return downloadsFolderPath;}
    public String getTestIPForConnection() {return testIPForConnection;}
    public String getAuthToken() {return authToken;}
    private static String[] getData() throws Exception {

        FileReader fr = new FileReader(pathToConfigFile);
        Scanner scan = new Scanner(fr);

        String[] data = new String[8];

        if (scan.hasNextLine()) {
            data[0] = scan.nextLine() + ":" + scan.nextLine();
            for (int i = 1; i <= 7; i++) {
                data[i] = scan.nextLine();
            }
        }

        fr.close();

        return data;
    }

    public void fullShortRegistrationForm(String name,
                                        String email,
                                        int country,
                                        int subscription,
                                        String password,
                                        String confirmPassword) {

        createAccountPage.getInputName().setValue(name);
        createAccountPage.getInputEmailAddress().setValue(email);
        createAccountPage.getSelectCountry().selectOption(country);
        createAccountPage.getSelectSubscription().selectOption(subscription);
        createAccountPage.getInputPassword().setValue(password);
        createAccountPage.getInputConfirmPassword().setValue(confirmPassword);
        createAccountPage.getSignUpButton().click();
    }

    public void fullAllRegistrationForm(String name,
                                                   String lastName,
                                                   String email,
                                                   int country,
                                                   int subscription,
                                                   String trackName,
                                                   int type,
                                                   String city,
                                                   String address,
                                                   String password,
                                                   String confirmPassword) {

        createAccountPage.getInputName().setValue(name);
        createAccountPage.getInputLastName().setValue(lastName);
        createAccountPage.getInputEmailAddress().setValue(email);
        createAccountPage.getSelectCountry().selectOption(country);
        createAccountPage.getSelectSubscription().selectOption(subscription);
        createAccountPage.getInputTrackName().setValue(trackName);
        if (type == 0 | type == 1 | type == 2 | type == 3) {
            createAccountPage.getSelectType().selectOption(type);
        }
        createAccountPage.getInputCity().setValue(city);
        createAccountPage.getInputAddress().setValue(address);
        createAccountPage.getInputPassword().setValue(password);
        createAccountPage.getInputConfirmPassword().setValue(confirmPassword);
        createAccountPage.getSignUpButton().click();

    }

    public void createRegistrationData() {
        Selenide.open(urlMainPage + "signup");
        countryNumber = (int) (Math.random()*250);
        userEmail = "juli+test" + RandomStringUtils.randomNumeric(10) + "@rcgtiming.com";
        System.out.println("countryNumber: " + countryNumber);
        System.out.println("userEmail: " + userEmail);
    }

    public void activateAccount() throws InterruptedException, MessagingException, IOException {
        ReadEmail readEmail = new ReadEmail();
        Thread.sleep(4000);
        String href = ReadEmail.getActivateHref(userEmail);
        if (!href.isEmpty()) {
            Selenide.open(href);
        }
    }

    public void userLogOut() {
        mainPage.getButtonUsersMenu().click();
        mainPage.getMenuExit().click();
    }

    public void userSignIn(String email, String password) throws InterruptedException {
        mainPage.getButtonSignIn().click();
        Thread.sleep(2000);
        mainPage.getFieldEmailAddressInSignInWindow().setValue(email);
        mainPage.getFieldPasswordInSignInWindow().setValue(password);
        mainPage.getButtonSignInInSignInWindow().click();
    }

    public void deleteUser() throws InterruptedException {
        Selenide.open(urlMainPage);
        mainPage.getButtonSignIn().click();
        mainPage.getFieldEmailAddressInSignInWindow().setValue(adminEmail);
        mainPage.getFieldPasswordInSignInWindow().setValue(adminPass);
        mainPage.getButtonSignInInSignInWindow().click();
        AdminPanelPage adminPanelPage = new AdminPanelPage();
        adminPanelPage.getMenuItemUsers().click();
        adminPanelPage.getButtonDeleteTopUser().click();
        adminPanelPage.getButtonUserDeleteConfirmation().click();
        adminPanelPage.getMenuAdmin().click();
        Thread.sleep(500);
        adminPanelPage.getMenuItemExit().click();
        Thread.sleep(500);
        mainPage.getButtonSignIn();
    }

    public void eventCreation(String eventName) throws InterruptedException {
        Selenide.open(eventsURL);
        eventsPage.getButtonCreate().click();
        eventsPage.getEventTitleField().setValue(eventName);
        eventsPage.getButtonSave().click();
        eventsPage.setParameters(eventName);
        Thread.sleep(3000);
    }

    public void eventDelete() {
        Selenide.open(eventsURL);
        eventsPage.getButtonDeleteEvent().click();
        eventsPage.getCheckConfirmationEventDelete().click();
        eventsPage.getButtonConfirmEventDelete().click();
    }

}
