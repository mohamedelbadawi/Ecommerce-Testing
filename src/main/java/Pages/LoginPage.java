package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By loginTitle = By.xpath("//div[@class=\"login-form\"]//h2");
    private final By loginEmail = By.cssSelector("[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("[data-qa='login-password']");
    private final By loginButton = By.cssSelector("[data-qa='login-button']");
    private final By errorMessage = By.xpath("//p[contains(text(),'Your email or password is incorrect!')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginTitleDisplayed() {
        waitForVisible(driver.findElement(loginTitle));
        String signupTitleText = driver.findElement(loginTitle).getText();
        return signupTitleText.equals("Login to your account");
    }

    public LoginPage enterLoginEmail(String email) {
        sendKeys(driver.findElement(loginEmail), email);
        return this;
    }

    public LoginPage enterLoginPassword(String password) {
        sendKeys(driver.findElement(loginPassword), password);
        return this;
    }

    public LoginPage clickLoginButton() {
        click(driver.findElement(loginButton));
        return this;
    }

    public boolean isErrorMessageDisplayed() {
        waitForVisible(driver.findElement(errorMessage));
        return driver.findElement(errorMessage).isDisplayed();
    }

}
