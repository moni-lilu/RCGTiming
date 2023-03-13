package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class CompetitionHeatPage {
    public String buttonHeatDetails = "//button[text()=' Heat Details                ']";
    public String buttonAddDriver = "//div[@id='adminCompetitionGroupDriversPopup']//button[text()=' Add Driver            ']";
    public String greenButtonAtHeatDetailsModalWindow = "//form[@action='Timing?action=EditCompetitionSectionGroupDriver']/button";
    public String buttonOk = "//div[@id='adminCompetitionGroupDriversPopup']//button[text()='Ok']";
    public String buttonStartCountdown = "//button[@id='race-countdown']";
    public String buttonManualLapsInput = "//button[@data-original-title='Manual Laps Input']";
    public String buttonPointer = "//div[@role='group']//button[@data-number='1']";
    public String lastLap = "//td[contains(@class, 'last-lap')]";
    public String buttonRaceFinish = "//button[@id='race-finish']";
    public String buttonConfirm = "//div[@id='doubleConfirmPopup']//button[text()='Confirm']";
    public SelenideElement getButtonHeatDetails() {
        return $(byXpath(buttonHeatDetails)).shouldHave(Condition.visible);
    }
    public SelenideElement getButtonAddDriver() {
        return $(byXpath(buttonAddDriver)).shouldHave(Condition.visible);
    }
    public SelenideElement getGreenButtonAtHeatDetailsModalWindow() {
        return $(byXpath(greenButtonAtHeatDetailsModalWindow)).shouldHave(Condition.visible);
    }
    public SelenideElement getButtonOk() {
        return $(byXpath(buttonOk)).shouldHave(Condition.visible);
    }
    public SelenideElement getButtonStartCountdown() {
        return $(byXpath(buttonStartCountdown)).shouldHave(Condition.visible);
    }
    public SelenideElement getButtonManualLapsInput() {
        return $(byXpath(buttonManualLapsInput)).shouldHave(Condition.visible);
    }
    public SelenideElement getButtonPointer() {
        return $(byXpath(buttonPointer)).shouldHave(Condition.visible);
    }
    public SelenideElement getLastLap() {
        return $(byXpath(lastLap)).shouldHave(Condition.visible);
    }
    public SelenideElement getButtonRaceFinish() {
        return $(byXpath(buttonRaceFinish)).shouldHave(Condition.visible);
    }
    public SelenideElement getButtonConfirm() {
        return $(byXpath(buttonConfirm)).shouldHave(Condition.visible);
    }
}
