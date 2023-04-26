package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class EventsPage {
    public String buttonCreate = "//span[text()='Create']";
    public String eventTitleField = "//input[@name='title']";
    public String eventTrackField = "(//form[@role='competition-edit']//a[@href='javascript:void(0)'])[1]";
    public String buttonSave = "//button[text()='Save']";
    public String buttonDeleteEvent = "";//"//button[@data-original-title='Delete']";
    public String checkConfirmationEventDelete = "//input[@class='confirmation-check']";
    public String buttonConfirmEventDelete = "(//button[text()='Confirm'])[3]";
    public String checkPublicEvent = "//form[@role='competition-edit']//input[@name='public']";
    public String eye = "(//table[@id='competitionList']/tbody/tr/td/span)[1]";
    public String eventStartDateOnTheForm = "//input[@name='start']";
    public String eventEndDateOnTheForm = "//input[@name='end']";
    public String datesError = "//span[text()=\"End Date shouldn't go before Start Date\"]";
    public String titleEmptyError = "//span[text()=\"Title couldn't be empty\"]";
    public String track = "//div[@id='select2-drop']//li[@role='presentation']";
    public String eventTitleInTheTable = "";
    public String eventStartDateInTheTable = "";
    public String eventEndDateInTheTable = "";
    public String eventTrackTitleInTheTable = "";
    public String eventOwnerInTheTable = "";


    public void setParameters(String eventTitle) {
        eventTitleInTheTable = "//a[contains(text(), '" + eventTitle + "')]";
        System.out.println("eventTitleInTheTable = " + eventTitleInTheTable);
        eventStartDateInTheTable = "(//td/a[contains(text(), '" + eventTitle + "')]/parent::td)/following-sibling::*[1]";
        eventEndDateInTheTable = "(//td/a[contains(text(), '" + eventTitle + "')]/parent::td)/following-sibling::*[1]/span";
        eventTrackTitleInTheTable = "(//td/a[contains(text(), '" + eventTitle + "')]/parent::td)/following-sibling::*[3]/a";
        eventOwnerInTheTable = "(//td/a[contains(text(), '" + eventTitle + "')]/parent::td)/following-sibling::*[4]";
    }
    public SelenideElement getButtonCreate() {
        return $(byXpath(buttonCreate)).shouldHave(Condition.visible);
    }
    public SelenideElement getEventTitleField() {
        return $(byXpath(eventTitleField)).shouldHave(Condition.visible);
    }

    public SelenideElement getButtonSave() {
        return $(byXpath(buttonSave)).shouldHave(Condition.visible);
    }

    public SelenideElement getTitleEmptyError() {
        return $(byXpath(titleEmptyError)).shouldHave(Condition.visible);
    }

    public SelenideElement getEye() {
        return $(byXpath(eye)).shouldHave(Condition.visible);
    }

    public SelenideElement getEventTitleInTheTable() {
        System.out.println("get eventTitleInTheTable = " + eventTitleInTheTable);
        return $(byXpath(eventTitleInTheTable)).shouldHave(Condition.visible);
    }

    public SelenideElement getEventStartDateInTheTable() {
        return $(byXpath(eventStartDateInTheTable)).shouldHave(Condition.visible);
    }

    public SelenideElement getEventEndDateInTheTable() {
        return $(byXpath(eventEndDateInTheTable)).shouldHave(Condition.visible);
    }

    public SelenideElement getEventTrackTitleInTheTable() {
        return $(byXpath(eventTrackTitleInTheTable)).shouldHave(Condition.visible);
    }

    public SelenideElement getEventOwnerInTheTable() {
        return $(byXpath(eventOwnerInTheTable)).shouldHave(Condition.visible);
    }

    public SelenideElement getButtonDeleteEvent(String eventName) {
        buttonDeleteEvent = "//a[contains(text(), '" + eventName + "')]/parent::*/following-sibling::*/button[@data-original-title='Delete']";
        return $(byXpath(buttonDeleteEvent)).shouldHave(Condition.visible);
    }

    public SelenideElement getCheckConfirmationEventDelete() {
        return $(byXpath(checkConfirmationEventDelete)).shouldHave(Condition.visible);
    }

    public SelenideElement getButtonConfirmEventDelete() {
        return $(byXpath(buttonConfirmEventDelete)).shouldHave(Condition.visible);
    }

    public SelenideElement getCheckPublicEvent() {
        return $(byXpath(checkPublicEvent)).shouldHave(Condition.visible);
    }

    public SelenideElement getEventStartDateOnTheForm() {
        return $(byXpath(eventStartDateOnTheForm)).shouldHave(Condition.visible);
    }

    public SelenideElement getEventEndDateOnTheForm() {
        return $(byXpath(eventEndDateOnTheForm)).shouldHave(Condition.visible);
    }

    public SelenideElement getDatesError() {
        return $(byXpath(datesError)).shouldHave(Condition.visible);
    }

    public SelenideElement getEventTrackField() {
        return $(byXpath(eventTrackField)).shouldHave(Condition.visible);
    }

    public SelenideElement getTrack() {
        return $(byXpath(track)).shouldHave(Condition.visible);
    }


}
