package ui;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.hamcrest.core.StringContains.containsString;

public class AuthorizationTest {

    static Controller controller = new Controller();
    private String name = "Paulo";
    private String userEmail;
    private int countryNumber;
    private String password = "123456789a";
    private MainPage mainPage = new MainPage();
    private SettingsPage settingsPage = new SettingsPage();
    Boolean userCreated = false;


    public AuthorizationTest() {
    }

    @After
    public void deleteCreatedUser() throws InterruptedException {
        if (userCreated) {
            controller.deleteUser();
            userCreated = false;
        }
    }

    @Test
    public void shouldSignInUserWithEmailAndPasswordIfUserWithSuchEmailAndPasswordWasRegistered() throws MessagingException, IOException, InterruptedException {
        createAccount();
        checkUserCreation();
        controller.activateAccount();
        controller.userLogOut();
        controller.userSignIn(userEmail, password);
        mainPage.getButtonUsersMenu().click();
        mainPage.getMenuSettings().click();
        Assert.assertEquals(userEmail, settingsPage.getOwnerEmail().getText());
        controller.userLogOut();
    }

    @Test
    public void shouldNotSignInIfUserNotActivateHisAccount() throws InterruptedException {
        createAccount();
        checkUserCreation();
        controller.userSignIn(userEmail, password);
        Assert.assertEquals("Please sign in", mainPage.getModalHeaderPleaseSignIn().getText());
    }

    @Test
    public void shouldNotSignInUserWithActivatedAccountIfUserTryToSignInWithWrongEmail() throws MessagingException, IOException, InterruptedException {
        createAccount();
        checkUserCreation();
        controller.activateAccount();
        controller.userLogOut();
        controller.userSignIn("wrong@gmail.test", password);
        Assert.assertEquals("Please sign in", mainPage.getModalHeaderPleaseSignIn().getText());
    }

    @Test
    public void shouldNotSignInUserWithActivatedAccountIfUserTryToSignInWithWrongPassword() throws MessagingException, IOException, InterruptedException {
        createAccount();
        checkUserCreation();
        controller.activateAccount();
        controller.userLogOut();
        controller.userSignIn(userEmail, "wrongPassword");
        Assert.assertEquals("Please sign in", mainPage.getModalHeaderPleaseSignIn().getText());
    }

    public void createAccount() {
        controller.createRegistrationData();
        userEmail = controller.userEmail;
        countryNumber = controller.countryNumber;
        controller.fullShortRegistrationForm(name, userEmail, countryNumber, 1, password, password);
    }

    public void checkUserCreation() {
        LoginSuccessPage loginSuccessPage = new LoginSuccessPage();
        MatcherAssert.assertThat(loginSuccessPage.getTextCongratulations().getText(), containsString("Congratulations!"));
        userCreated = true;
    }

}
