package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By loginRegisterUrl = By.xpath("//a[contains(normalize-space(),'Login')]");
    private final By logoutButton = By.xpath("//a[contains(normalize-space(),'Logout')]");
    private final By loggedInButton = By.xpath("//a[contains(normalize-space(),'Logged in as')]");
    private final By deleteAccountButton = By.xpath("//a[contains(normalize-space(),'Delete Account')]");
    private final By contactUsButton = By.xpath("//a[contains(normalize-space(),'Contact us')]");
    private final By productsButton = By.xpath("//a[contains(normalize-space(),'Products')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickLoginRegisterUrl() {
        click(driver.findElement(loginRegisterUrl));
    }

    public void clickLogoutButton() {
        click(driver.findElement(logoutButton));
    }

    public boolean isUserLoggedIn() {
        waitForVisible(driver.findElement(loggedInButton));
        return driver.findElement(loggedInButton).isDisplayed();

    }

    public boolean isHomePage() {
        String title = driver.getTitle();
        return title.equals("Automation Exercise");
    }

    public void clickDeleteAccountButton() {
        click(driver.findElement(deleteAccountButton));
    }

    public void clickContactUsButton() {
        click(driver.findElement(contactUsButton));
    }

    public void clickProductsButton() {
        click(driver.findElement(productsButton));
    }

}
