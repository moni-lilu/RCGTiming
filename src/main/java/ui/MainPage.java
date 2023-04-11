package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    public String buttonSignInXpath = "//header/div/div/div/div/a[text()='Sign In']";
    public String hrefSignUpInSignInWindow = "html/body/div/div/div/div/a";
    public String hrefForgotPasswordInSignInWindow = "html/body/div/div/div/div/div/a";
    public String hrefSignUpInPasswordRecoveryWindow = "html/body/div[2]/div/div/div[3]/a";
    public String fieldEmailAddressInSignInWindow = "//div[@id='loginModal']/div/div/div/form/div/input[@placeholder='Email address']";
    public String fieldPasswordInSignInWindow = "//div[@id='loginModal']/div/div/div/form/div/input[@placeholder='Password']";
    public String buttonSignInInSignInWindow = "//button[text()='Sign in']";
    public String usersMenu = "(//a[@href='#'])[2]";
    public String menuExit = "//a[@id='btnExit']";
    public String menuSettings = "//a[@href='Settings']";
    public String modalHeaderPleaseSignIn = "//h4[text()='Please sign in']";
    public String eventsButton = "//a[@href='Timing/Competitions']";
    public String downloadButtonInMainPage = "//button[@data-target='#adminTimingDownloadPopup']";
    public String fullPackageButton = "//button[@data-value='full']";
    public String configurationUpdateButton = "//button[@data-value='config']";
    public String driverUpdateButton = "//button[@data-value='update']";
    public String downloadButtonInModalWindow = "//div[@class='modal-content']//button[text()=' Download']";
    public String selectDecoder = "//select[@name='decoder']";
    public String selectProtocol = "//select[@name='protocol']";
    public String inputIP = "//input[@name='ip']";
    public String inputPort = "//input[@name='port']";


    public SelenideElement getButtonSignIn() {
        return $(byXpath(buttonSignInXpath)).shouldHave(Condition.visible);
    }

    public SelenideElement getHrefSignUpInSignInWindow() {
        return $(byXpath(hrefSignUpInSignInWindow)).shouldHave(Condition.visible);
    }

    public SelenideElement getHrefForgotPasswordInSignInWindow() {
        return $(byXpath(hrefForgotPasswordInSignInWindow)).shouldHave(Condition.visible);
    }

    public SelenideElement getHrefSignUpInPasswordRecoveryWindow() {
        return $(byXpath(hrefSignUpInPasswordRecoveryWindow)).shouldHave(Condition.visible);
    }

    public SelenideElement getFieldEmailAddressInSignInWindow() {
        return $(byXpath(fieldEmailAddressInSignInWindow)).shouldHave(Condition.visible);
    }

    public SelenideElement getFieldPasswordInSignInWindow() {
        return $(byXpath(fieldPasswordInSignInWindow)).shouldHave(Condition.visible);
    }

    public SelenideElement getButtonSignInInSignInWindow() {
        return $(byXpath(buttonSignInInSignInWindow)).shouldHave(Condition.visible);
    }

    public SelenideElement getButtonUsersMenu() {
        return $(byXpath(usersMenu)).shouldHave(Condition.visible);
    }

    public SelenideElement getMenuExit() {
        return $(byXpath(menuExit)).shouldHave(Condition.visible);
    }

    public SelenideElement getMenuSettings() {
        return $(byXpath(menuSettings)).shouldHave(Condition.visible);
    }

    public SelenideElement getModalHeaderPleaseSignIn() {return $(byXpath(modalHeaderPleaseSignIn)).shouldHave(Condition.visible);}
    public SelenideElement getEventsButton() {return $(byXpath(eventsButton)).shouldHave(Condition.visible);}
    public SelenideElement getDownloadButtonInMainPage() {return $(byXpath(downloadButtonInMainPage)).shouldHave(Condition.visible);}
    public SelenideElement getFullPackageButton() {return $(byXpath(fullPackageButton)).shouldHave(Condition.visible);}

    public SelenideElement getConfigurationUpdateButton() {return $(byXpath(configurationUpdateButton)).shouldHave(Condition.visible);}
    public SelenideElement getDriverUpdateButton() {return $(byXpath(driverUpdateButton)).shouldHave(Condition.visible);}
    public SelenideElement getDownloadButtonInModalWindow() {return $(byXpath(downloadButtonInModalWindow)).shouldHave(Condition.visible);}
    public SelenideElement getSelectDecoder() {
        return $(byXpath(selectDecoder)).shouldHave(Condition.visible);
    }
    public SelenideElement getSelectProtocol() {
        return $(byXpath(selectProtocol)).shouldHave(Condition.visible);
    }
    public SelenideElement getInputIP() {
        return $(byXpath(inputIP)).shouldHave(Condition.visible);
    }
    public SelenideElement getInputPort() {
        return $(byXpath(inputPort)).shouldHave(Condition.visible);
    }
}
