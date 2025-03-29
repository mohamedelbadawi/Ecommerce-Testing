package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class HomePage extends BasePage {
    private final By loginRegisterUrl = By.xpath("//a[contains(normalize-space(),'Login')]");
    private final By logoutButton = By.xpath("//a[contains(normalize-space(),'Logout')]");
    private final By loggedInButton = By.xpath("//a[contains(normalize-space(),'Logged in as')]");
    private final By deleteAccountButton = By.xpath("//a[contains(normalize-space(),'Delete Account')]");
    private final By contactUsButton = By.xpath("//a[contains(normalize-space(),'Contact us')]");
    private final By productsButton = By.xpath("//a[contains(normalize-space(),'Products')]");
    private final By subscriptionTitle = By.cssSelector(".single-widget h2");
    private final By footer = By.id("footer");
    private final By subscribeEmailInput = By.id("susbscribe_email");
    private final By subscribeButton = By.id("subscribe");
    private final By subscriptionSuccessAlert = By.id("success-subscribe");

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

    public HomePage scrollToFooter() {
        scrollToElement(driver.findElement(footer));
        return this;
    }

    public boolean isSubscriptionTitleDisplayed() {
        return isElementDisplayed(subscriptionTitle);
    }

    public HomePage enterSubscriptionEmail(String email) {
        sendKeys(driver.findElement(subscribeEmailInput), email);
        return this;
    }

    public void clickSubscribeButton() {
        click(driver.findElement(subscribeButton));
    }

    public boolean isSubscriptionSuccessAlertDisplayed() {
        String classAttr = driver.findElement(subscriptionSuccessAlert).getDomAttribute("class");
        List<String> classes = List.of(classAttr.split("\\s+"));
        if (!classes.contains("hide")) {
            return true;
        }

        return false;
    }

}
