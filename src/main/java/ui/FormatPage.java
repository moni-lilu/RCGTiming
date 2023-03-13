package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class FormatPage {
    public String seedingTab = "//form/ul/li/a[contains(text(), 'Seeding')]";
    public String buttonSaveInFormatTab = "//div[@id='formatTab']/div/button[contains(text(), 'Save')]";
    public String singleBestLapRadioButton = "//div[contains(@class, 'tab-pane') and contains(@class, 'active')]/div/div/label/input[@value='rankOrderBestLap']";
    public String bestThreeLapRadioButton = "//div[contains(@class, 'tab-pane') and contains(@class, 'active')]/div/div/label/input[@value='rankOrderPractice']";
    public String numberOfLapsForThePersonalTimeRadioButton = "//div[contains(@class, 'tab-pane') and contains(@class, 'active')]/div/div/label/input[@value='rankOrderQualify']";
    public String numberOfLapsForTheOverallTimeRadioButton = "//div[contains(@class, 'tab-pane') and contains(@class, 'active')]/div/div/label/input[@value='rankOrderFinal']";


    public SelenideElement getSeedingTab() {
        return $(byXpath(seedingTab)).shouldHave(Condition.visible);
    }
    public SelenideElement getButtonSaveInFormatTab() {
        return $(byXpath(buttonSaveInFormatTab)).shouldHave(Condition.visible);
    }
    public SelenideElement getSingleBestLapRadioButton() {
        return $(byXpath(singleBestLapRadioButton)).shouldHave(Condition.visible);
    }
    public SelenideElement getBestThreeLapRadioButton() {
        return $(byXpath(bestThreeLapRadioButton)).shouldHave(Condition.visible);
    }
    public SelenideElement getNumberOfLapsForThePersonalTimeRadioButton() {
        return $(byXpath(numberOfLapsForThePersonalTimeRadioButton)).shouldHave(Condition.visible);
    }
    public SelenideElement getNumberOfLapsForTheOverallTimeRadioButton() {
        return $(byXpath(numberOfLapsForTheOverallTimeRadioButton)).shouldHave(Condition.visible);
    }
}
