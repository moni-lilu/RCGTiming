package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.apache.commons.lang3.RandomStringUtils;
import org.ini4j.Ini;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Controller {
    public static String secretPath = "/run/secrets/rcgt_config";
    public static String pathToConfig = "./config.ini";
    private static String urlMainPage = "";
    private static String adminEmail = "";
    private static String adminPass = "";
    private static String testEmailForActivation = "";
    private static String secretPasswordForEmail = "";
    private static String downloadsFolderPath = "";
    private static String testIPForConnection = "";
    private static String authToken = "";
    static {
        try {
            getConfiguration();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String eventsURL = "https://stage.rcgtiming.com/Timing/Competitions";
    EventsPage eventsPage = new EventsPage();
    int countryNumber;
    String userEmail;
    MainPage mainPage = new MainPage();
    CreateAccountPage createAccountPage = new CreateAccountPage();

  /*  public Controller() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
    }*/
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

    private static void getConfiguration() throws IOException {
        String pathToFile = "";

        File configFile = new File(pathToConfig);
        if (configFile.exists()) {
            pathToFile = pathToConfig;
        } else {
            pathToFile = secretPath;
        }
        File fileToParse = new File(pathToFile);
        Ini ini = new Ini(fileToParse);
        urlMainPage = "https://"
                + ini.get("accesses", "StageLogin")
                + ":"
                + ini.get("accesses", "StagePassword")
                + "@stage.rcgtiming.com/";
        adminEmail = ini.get("accesses", "AdminEmail");
        adminPass = ini.get("accesses", "AdminPassword");
        testEmailForActivation = ini.get("accesses", "TestEmail");
        secretPasswordForEmail = ini.get("accesses", "TestPassword");
        downloadsFolderPath = ini.get("configuration", "DownloadsFolderPath");
        testIPForConnection = ini.get("configuration", "IPForConnaction");
        authToken = ini.get("configuration", "AuthorisationToken");
        Boolean headless = Boolean.valueOf(ini.get("configuration", "Headless"));


        System.out.println(urlMainPage);
        System.out.println(headless);

        if (headless) {
            // изменение конфигурации браузера для корректной работы в Docker-конейнере
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            Configuration.browserCapabilities = chromeOptions;
        }
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
        //ReadEmail readEmail = new ReadEmail();
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
