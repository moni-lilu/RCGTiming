package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class AdminPanelPage {
    public String menuItemUsers = "//a[text()='Пользователи']";
    public String buttonDeleteTopUser = "//td[9]/button[5]";
    public String buttonUserDeleteConfirmation = "//div[@class='modal fade in']/div/div/div//button[text()='Confirm']";

    public String menuAdmin = "//body/header/div/div/div[2]/ul[2]/li[3]/a";
    public String menuItemExit = "//a[@id='btnExit']";

    public SelenideElement getMenuItemUsers() {
        return $(byXpath(menuItemUsers)).shouldHave(Condition.visible);
    }

    public SelenideElement getButtonDeleteTopUser() {
        return $(byXpath(buttonDeleteTopUser)).shouldHave(Condition.visible);
    }

    public SelenideElement getButtonUserDeleteConfirmation() {
        return $(byXpath(buttonUserDeleteConfirmation)).shouldHave(Condition.visible);
    }

    public SelenideElement getMenuAdmin() {
        return $(byXpath(menuAdmin)).shouldHave(Condition.visible);
    }

    public SelenideElement getMenuItemExit() {
        return $(byXpath(menuItemExit)).shouldHave(Condition.visible);
    }
}
