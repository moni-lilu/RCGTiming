package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.checkerframework.checker.units.qual.C;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class CreateAccountPage {
    public String inputNameXpath = "//input[@id='nameField']";
    public String inputLastName = "//input[@id='lastNameField']";
    public String inputEmailAddressXpath = "//input[@id='emailField']";
    public String selectCountryXpath = "//select[@id='countryField']";
    public String selectSubscriptionXpath = "//select[@name='subscription']";
    public String inputTrackName = "//input[@name='trackName']";
    public String selectType = "//select[@name='trackType']";
    public String inputCity = "//input[@name='trackCity']";
    public String inputAddress = "//input[@name='trackAddress']";
    public String inputPasswordXpath = "//input[@id='passField']";
    public String inputConfirmPasswordXpath = "//input[@id='passField2']";






    public String buttonSignUpOnCreateAccountPage = "//button[text()='Sign Up']";
    public String errorNameCantBeEmpty = "//p[text()=\"Name can't be empty\"]";
    public String errorNameShouldNotContainSpecialSymbols = "//p[text()=\"Name should not contain special characters and digits\"]";
    public String errorEmailCantBeEmpty = "//p[text()=\"Email can't be empty\"]";
    public String errorEmptyPassword = "//html/body/div[4]/form/div[9]";
    public String errorEmptyConfirmPassword = "//html/body/div[4]/form/div[10]";
    public String errorNameShouldBeFiftyCharactersLongAtMost = "//p[text()='Name should be 50 characters long at most']";
    public String errorInvalidEmailAddress = "//p[text()='Invalid Email Address']";
    public String errorEmailAddressLength = "//p[text()='Email should be 100 characters long at most']";
    public String errorPasswordShouldBeTenCharactersAtLeast = "//p[text()='Password should be at least 10 characters long']";
    public String errorPasswordShouldMatch = "//p[text()='Passwords should match']";
    public String errorUserWithThisEmailAlreadyRegistered = "//p[text()='User with this Email already registered']";


    public SelenideElement getInputName() {
        return $(byXpath(inputNameXpath)).shouldHave(Condition.visible);
    }
    public SelenideElement getInputLastName() {
        return $(byXpath(inputLastName)).shouldHave(Condition.visible);
    }

    public SelenideElement getInputEmailAddress() {
        return $(byXpath(inputEmailAddressXpath)).shouldHave(Condition.visible);
    }

    public SelenideElement getSelectCountry() {
        return $(byXpath(selectCountryXpath)).shouldHave(Condition.visible);
    }

    public SelenideElement getSelectSubscription() {
        return $(byXpath(selectSubscriptionXpath)).shouldHave(Condition.visible);
    }

    public SelenideElement getInputTrackName() {
        return $(byXpath(inputTrackName)).shouldHave(Condition.visible);
    }
    public SelenideElement getSelectType() {
        return $(byXpath(selectType)).shouldHave(Condition.visible);
    }
    public SelenideElement getInputCity() {
        return $(byXpath(inputCity)).shouldHave(Condition.visible);
    }
    public SelenideElement getInputAddress() {
        return $(byXpath(inputAddress)).shouldHave(Condition.visible);
    }

    public SelenideElement getInputPassword() {
        return $(byXpath(inputPasswordXpath)).shouldHave(Condition.visible);
    }

    public SelenideElement getInputConfirmPassword() {
        return $(byXpath(inputConfirmPasswordXpath)).shouldHave(Condition.visible);
    }

    public SelenideElement getSignUpButton() {
        return $(byXpath(buttonSignUpOnCreateAccountPage)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorNameCantBeEmpty() {
        return $(byXpath(errorNameCantBeEmpty)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorNameShouldNotContainSpecialSymbols() {
        return $(byXpath(errorNameShouldNotContainSpecialSymbols)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorEmailCantBeEmpty() {
        return $(byXpath(errorEmailCantBeEmpty)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorEmptyPassword() {
        return $(byXpath(errorEmptyPassword)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorEmptyConfirmPassword() {
        return $(byXpath(errorEmptyConfirmPassword)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorNameShouldBeFiftyCharactersLongAtMost() {
        return $(byXpath(errorNameShouldBeFiftyCharactersLongAtMost)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorInvalidEmailAddress() {
        return $(byXpath(errorInvalidEmailAddress)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorEmailAddressLength() {
        return $(byXpath(errorEmailAddressLength)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorPasswordShouldBeTenCharactersAtLeast() {
        return $(byXpath(errorPasswordShouldBeTenCharactersAtLeast)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorPasswordShouldMatch() {
        return $(byXpath(errorPasswordShouldMatch)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorUserWithThisEmailAlreadyRegistered() {
        return $(byXpath(errorUserWithThisEmailAlreadyRegistered)).shouldHave(Condition.visible);
    }

}
