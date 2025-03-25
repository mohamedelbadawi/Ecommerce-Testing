package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By loginRegisterUrl = By.xpath("//a[contains(text(),'Login')]");
    private final By logoutButton = By.xpath("//a[contains(text(),'Logout')]");
    private final By loggedInButton = By.xpath("//a[contains(text(),'Logged in as')]");
    private final By deleteAccountButton = By.xpath("//a[contains(text(),'Delete Account')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickLoginRegisterUrl() {
        click(driver.findElement(loginRegisterUrl));
        return this;
    }

    public HomePage clickLogoutButton() {
        click(driver.findElement(logoutButton));
        return this;
    }

    public boolean isUserLoggedIn() {
        waitForVisible(driver.findElement(loggedInButton));
        return driver.findElement(loggedInButton).isDisplayed();

    }

    public boolean isHomePage() {
        String title = driver.getTitle();
        return title.equals("Automation Exercise");
    }

    public HomePage clickDeleteAccountButton() {
        click(driver.findElement(deleteAccountButton));
        return this;
    }
}
