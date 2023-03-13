package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class HeatResultsPage {

    public String goToResultsButton = "//a[@class='hidden-print']";
    public String raceSettingsButton = "//span[text()='Race Settings']/..";
    public String raceTypeValue = "//label[contains(text(), 'Race Type')]/following-sibling::select/option[@selected='true']";
    public String closeRaceSettingsWindowButton = "//h4[text()='Race Settings']/preceding-sibling::button";

    public SelenideElement getGoToResultsButton() {
        return $(byXpath(goToResultsButton)).shouldHave(Condition.visible);
    }

    public SelenideElement getRaceSettingsButton() {
        return $(byXpath(raceSettingsButton)).shouldHave(Condition.visible);
    }

    public SelenideElement getRaceTypeValue() {
        return $(byXpath(raceTypeValue)).shouldHave(Condition.visible);
    }

    public SelenideElement getCloseRaceSettingsWindowButton() {
        return $(byXpath(closeRaceSettingsWindowButton)).shouldHave(Condition.visible);
    }



}
