package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class TracksPage {
    public String buttonAdd = "//button[text()=' Add    ']";
    public String trackTitle = "//form[@role='track-edit']//input[@name='name']";
    public String selectTrackCountry = "//form[@role='track-edit']//a[@href='javascript:void(0)'][1]";
    public String buttonConfirm = "//div[@class='modal-content']//button[text()='Confirm']";
    public String buttonDeleteTrack = "//table//button";
    public String buttonShow = "//input[@value='Show']";
    public String country = "";
    public String countryFilter = "//form[@action='/Timing/Tracks']//a[@href='javascript:void(0)']";

    public void setCountry(String countryName) {
        country = "//li/div[text()=\"" + countryName + "\"]";
        System.out.println("From method set: " + countryName);
    }
    public SelenideElement getButtonAdd() {
        return $(byXpath(buttonAdd)).shouldHave(Condition.visible);
    }

    public SelenideElement getTrackTitle() {
        return $(byXpath(trackTitle)).shouldHave(Condition.visible);
    }

    public SelenideElement getSelectTrackCountry() {
        return $(byXpath(selectTrackCountry)).shouldHave(Condition.visible);
    }

    public SelenideElement getButtonConfirm() {
        return $(byXpath(buttonConfirm)).shouldHave(Condition.visible);
    }

    public SelenideElement getButtonDeleteTrack() {
        return $(byXpath(buttonDeleteTrack)).shouldHave(Condition.visible);
    }

    public SelenideElement getCountry() {
        return $(byXpath(country)).scrollTo().shouldHave(Condition.visible);
    }

    public SelenideElement getCountryFilter() {
        return $(byXpath(countryFilter)).scrollTo().shouldHave(Condition.visible);
    }
    public SelenideElement getButtonShow() {
        return $(byXpath(buttonShow)).scrollTo().shouldHave(Condition.visible);
    }
}
