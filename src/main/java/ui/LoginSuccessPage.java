package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class LoginSuccessPage {
    public String textCongratulations = "//h2";

    public SelenideElement getTextCongratulations() {
        return $(byXpath(textCongratulations)).shouldHave(Condition.visible);
    }
}
