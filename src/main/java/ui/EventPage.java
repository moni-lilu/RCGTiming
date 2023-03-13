package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class EventPage {
    public String addClassesButton = "//button[text() = ' Add Classes                ']";
    public String buggy2WDCheckbox = "//form[@action = 'Timing?action=ImportCompetition']//input[@value = 'Buggy 2wd']";
    public String buggy8Nitro = "//form[@action = 'Timing?action=ImportCompetition']//input[@value = 'Buggy 8 Nitro']";
    public String confirmButtonInAddClassesModalWindow = "//div[@id = 'adminCompetitionImportPopup']//button[text() = 'Confirm']";
    public String classesTab = "//ul[@role = 'tablist']//a[@data-target = 'classesTab']";
    public String driversTab = "//ul[@role = 'tablist']//a[@data-target = 'driversTab']";
    public String scheduleTab = "//ul[@role = 'tablist']//a[@data-target = 'scheduleTab']";
    public String formatTab = "//span[text()='Format']/..";
    public String addDriverEntryButton = "//div[@id = 'driversTab']//button[text() = ' Add Driver Entry        ']";
    public String classButtonInModalWindow = "";
    public String firstNameFieldInActiveInput = "";
    public String lastNameFieldInActiveInput = "";
    public String countryFieldInActiveInput = "";
    public String transponderFieldInActiveInput = "";
    public String buttonSaveInModalWindow = "//div[@id = 'adminCompetitionDriverPopup']//button[text() = 'Save']";
    public String itemInList = "";
    public String nameInTable = "//div[@id='driversTab']//a[@class='driver-edit']";
    public String classNameInTable = "(//div[@id='driversTab']//tbody/tr/td)[4]";
    public String transponderNumberInTable = "(//div[@id='driversTab']//tbody/tr/td)[5]";
    public String countryInTable = "(//div[@id='driversTab']//tbody/tr/td)[7]";
    public String adminDriverListSettingsButton = "//button[@data-target='#adminDriverListSettingsPopup']";
    public String countryCheckboxInListSettings = "//div[@id='adminDriverListSettingsPopup']//input[@value='country']";
    public String confirmButtonInListSettings = "//div[@id='adminDriverListSettingsPopup']//button[text()='Confirm']";
    public String deleteEntryButton = "//div[@id='driversTab']//tbody/tr/td/button[@data-confirm='Are you sure?']";
    public String errorEmptyFirstLastName = "//span[text() = 'First and Last Name should not be empty']";
    public String driverRegistrationForm = "//fieldset[@id = 'entrySection_']";
    public String modalWindowError = "//div[@id='adminCompetitionDriverPopup']//div[@role = 'alert']/span";
    public String errorInvalidTransponderNumber = "//span[text()='Invalid Transponder number']";
    public String transponderFieldInEditForm = "//form[@role='driver-entry']//input[@name='transponder']";
    public String cancelButtonInEditForm = "//div[@id='adminCompetitionDriverPopup']//button[text()='Cancel']";
    public String errorTooManyTransponders = "//span[text()='Too many transponders']";
    public String errorTransponderNumberDuplicate = "//span[text()='Transponder number duplicate ']";
    public String buttonRace = "//div[@id='scheduleTab']//button//span[text()='Race']";
    public String selectGroup = "//div[@id='adminCompetitionHeatPopup']//select[@name='id_group']";
    public String inputCountdown = "//div[@id='adminCompetitionHeatPopup']//input[@name='countdown']";
    public String buttonConfirmInRaceSettingsModalWindow = "//div[@id='adminCompetitionHeatPopup']//button[text()='Confirm']";
    public SelenideElement getAddClassesButton() {
        return $(byXpath(addClassesButton)).shouldHave(Condition.visible);
    }

    public SelenideElement getBuggy2WDCheckbox() {
        return $(byXpath(buggy2WDCheckbox)).shouldHave(Condition.visible);
    }

    public SelenideElement getBuggy8Nitro() {
        return $(byXpath(buggy8Nitro)).shouldHave(Condition.visible);
    }

    public SelenideElement getConfirmButtonInAddClassesModalWindow() {
        return $(byXpath(confirmButtonInAddClassesModalWindow)).shouldHave(Condition.visible);
    }
    public SelenideElement getClassesTab() {
        return $(byXpath(classesTab)).shouldHave(Condition.visible);
    }
    public SelenideElement getDriversTab() {
        return $(byXpath(driversTab)).shouldHave(Condition.visible);
    }
    public SelenideElement getScheduleTab() {
        return $(byXpath(scheduleTab)).shouldHave(Condition.visible);
    }
    public SelenideElement getFormatTab() {
        return $(byXpath(formatTab)).shouldHave(Condition.visible);
    }
    public SelenideElement getAddDriverEntryButton() {
        return $(byXpath(addDriverEntryButton)).shouldHave(Condition.visible);
    }
    public SelenideElement getClassButtonInModalWindow(String className) {
        classButtonInModalWindow = "//form[@action = 'Timing?action=EditCompetitionDriver']//button[contains(text(), '" + className + "')]";
        return $(byXpath(classButtonInModalWindow)).shouldHave(Condition.visible);
    }
    public SelenideElement getFirstNameInActiveInputForm(String entrySectionNumber) {
        firstNameFieldInActiveInput = "//form[@action = 'Timing?action=EditCompetitionDriver']//fieldset[@id = 'entrySection_" + entrySectionNumber + "']//input[@data-key = 'firstName']";
        return $(byXpath(firstNameFieldInActiveInput)).shouldHave(Condition.visible);
    }

    public SelenideElement getLastNameInActiveInputForm(String entrySectionNumber) {
        lastNameFieldInActiveInput = "//form[@action = 'Timing?action=EditCompetitionDriver']//fieldset[@id = 'entrySection_" + entrySectionNumber + "']//input[@data-key = 'lastName']";
        return $(byXpath(lastNameFieldInActiveInput)).shouldHave(Condition.visible);
    }

    public SelenideElement getCountryInActiveInputForm(String entrySectionNumber) {
        countryFieldInActiveInput = "//form[@action = 'Timing?action=EditCompetitionDriver']//fieldset[@id='entrySection_" + entrySectionNumber + "']//div[@id = 's2id_countryField']/a";
        return $(byXpath(countryFieldInActiveInput)).shouldHave(Condition.visible);
    }

    public SelenideElement getTransponderInActiveInputForm(String entrySectionNumber) {
        transponderFieldInActiveInput = "//form[@action = 'Timing?action=EditCompetitionDriver']//fieldset[@id = 'entrySection_" + entrySectionNumber + "']//input[@name = 'transponder[" + entrySectionNumber + "]']";
        return $(byXpath(transponderFieldInActiveInput)).shouldHave(Condition.visible);
    }
    public SelenideElement getButtonSaveInModalWindow() {
        return $(byXpath(buttonSaveInModalWindow)).shouldHave(Condition.visible);
    }

    public SelenideElement getItemInList(String country) {
        itemInList = "//div[@id = 'select2-drop']/ul/li/div[text() = '" + country + "']";
        return $(byXpath(itemInList)).shouldBe(Condition.visible);
    }

    public SelenideElement getErrorTransponderNumberDuplicate() {
        return $(byXpath(errorTransponderNumberDuplicate)).shouldBe(Condition.visible);
    }

    public SelenideElement getNameInTable() {
        return $(byXpath(nameInTable)).shouldHave(Condition.visible);
    }
    public SelenideElement getClassNameInTable() {
        return $(byXpath(classNameInTable)).shouldHave(Condition.visible);
    }
    public SelenideElement getTransponderNumberInTable() {
        return $(byXpath(transponderNumberInTable)).shouldHave(Condition.visible);
    }
    public SelenideElement getCountryInTable() {
        return $(byXpath(countryInTable)).shouldHave(Condition.visible);
    }
    public SelenideElement getAdminDriverListSettingsButton() {
        return $(byXpath(adminDriverListSettingsButton)).shouldHave(Condition.visible);
    }
    public SelenideElement getCountryCheckboxInListSettings() {
        return $(byXpath(countryCheckboxInListSettings)).shouldHave(Condition.visible);
    }
    public SelenideElement getConfirmButtonInListSettings() {
        return $(byXpath(confirmButtonInListSettings)).shouldHave(Condition.visible);
    }
    public SelenideElement getDeleteEntryButton() {
        return $(byXpath(deleteEntryButton)).shouldHave(Condition.visible);
    }

    public SelenideElement getErrorEmptyFirstLastName() {
        return $(byXpath(errorEmptyFirstLastName)).shouldHave(Condition.visible);
    }
    public SelenideElement getDriverRegistrationForm() {
        return $(byXpath(driverRegistrationForm)).shouldHave(Condition.visible);
    }
    public SelenideElement getModalWindowError() {
        return $(byXpath(modalWindowError)).shouldHave(Condition.visible);
    }

    public SelenideElement getInvalidTransponderNumberError() {
        return $(byXpath(errorInvalidTransponderNumber)).shouldHave(Condition.visible);
    }

    public SelenideElement getTransponderFieldInEditForm() {
        return $(byXpath(transponderFieldInEditForm)).shouldHave(Condition.visible);
    }

    public SelenideElement getCancelButtonInEditForm() {
        return $(byXpath(cancelButtonInEditForm)).shouldHave(Condition.visible);
    }
    public SelenideElement getErrorTooManyTransponders() {
        return $(byXpath(errorTooManyTransponders)).shouldHave(Condition.visible);
    }
    public SelenideElement getButtonRace() {
        return $(byXpath(buttonRace)).shouldHave(Condition.visible);
    }
    public SelenideElement getSelectGroup() {
        return $(byXpath(selectGroup)).shouldHave(Condition.visible);
    }
    public SelenideElement getInputCountdown() {
        return $(byXpath(inputCountdown)).shouldHave(Condition.visible);
    }
    public SelenideElement getButtonConfirmInRaceSettingsModalWindow() {
        return $(byXpath(buttonConfirmInRaceSettingsModalWindow)).shouldHave(Condition.visible);
    }
}
