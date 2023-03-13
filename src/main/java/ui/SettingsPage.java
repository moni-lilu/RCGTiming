package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class SettingsPage {
    public String ownerEmail = "//small";
    public String countryName = "//span[@id='select2-chosen-1']";

    public SelenideElement getOwnerEmail() {
        return $(byXpath(ownerEmail)).shouldHave(Condition.visible);
    }
    public SelenideElement getCountryName() {
        return $(byXpath(countryName)).shouldHave(Condition.visible);
    }
}
