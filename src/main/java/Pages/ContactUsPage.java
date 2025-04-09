package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactUsPage extends BasePage {
    private final By nameInput = By.xpath("//form[contains(@id,'contact')]//input[contains(@name,'name')]");
    private final By emailInput = By.xpath("//form[contains(@id,'contact')]//input[contains(@name,'email')]");
    private final By subjectInput = By.xpath("//form[contains(@id,'contact')]//input[contains(@name,'subject')]");
    private final By messageInput = By.xpath("//form[contains(@id,'contact')]//textarea[contains(@name,'message')]");
    private final By fileInput = By.xpath("//form[contains(@id,'contact')]//input[contains(@name,'upload')]");
    private final By submitButton = By.xpath("//form[contains(@id,'contact')]//input[contains(@name,'submit')]");

    private final By successMessage = By.cssSelector(".status.alert.alert-success");
    private final By getInTouchMessage = By.cssSelector(".contact-form h2");

    //Success! Your details have been submitted successfully.
    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    public ContactUsPage enterName(String name) {
        sendKeys(driver.findElement(nameInput), name);
        return this;
    }

    public ContactUsPage enterEmail(String email) {
        sendKeys(driver.findElement(emailInput), email);
        return this;
    }

    public ContactUsPage enterSubject(String subject) {
        sendKeys(driver.findElement(subjectInput), subject);
        return this;
    }

    public ContactUsPage enterMessage(String message) {
        sendKeys(driver.findElement(messageInput), message);
        return this;
    }

    public ContactUsPage enterFile(String file) {
        sendKeys(driver.findElement(fileInput), file);
        return this;
    }

    public ContactUsPage clickSubmitButton() {
        scrollToElement(driver.findElement(submitButton));
        click(driver.findElement(submitButton));
        return this;
    }

    public ContactUsPage acceptConfirmationAlert() {
        driver.switchTo().alert().accept();
        return this;
    }

    public boolean isSuccessMessageDisplayed() {
        waitForVisible(driver.findElement(successMessage));
        return driver.findElement(successMessage).isDisplayed();
    }

    public boolean isGetInTouchMessageDisplayed() {
        waitForVisible(driver.findElement(getInTouchMessage));
        return driver.findElement(getInTouchMessage).isDisplayed();

    }


}
