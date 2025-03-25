package Pages;

import Config.ConfigReader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BasePage {
    private static Logger logger;
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        logger = Logger.getLogger(this.getClass().getName());
    }

    protected String getPageTitle() {
        try {
            String title = driver.getTitle();
            logger.info("Page title retrieved: " + title);
            return title;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get page title", e);
            throw new RuntimeException(e);
        }
    }

    protected void waitForVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            logger.info("Element is visible: " + element);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Element not visible: " + element, e);
        }
    }

    protected void waitForClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            logger.info("Element is clickable: " + element);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Element not clickable: " + element, e);
        }
    }

    protected void sendKeys(WebElement element, String text) {
        try {
            waitForVisible(element);
            element.clear();
            element.sendKeys(text);
            logger.info("Sent keys to element: " + element + " | Text: " + text);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to send keys to element: " + element, e);
        }
    }

    protected void click(WebElement element) {
        try {
            waitForClickable(element);
            element.click();
            logger.info("Clicked on element: " + element);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to click element: " + element, e);
        }
    }

    protected void selectByValue(WebElement dropdownElement, String value) {
        try {
            waitForVisible(dropdownElement);
            Select select = new Select(dropdownElement);
            select.selectByValue(value);
            logger.info("Selected value: " + value + " from dropdown: " + dropdownElement);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to select by value: " + value, e);
        }
    }

    protected void selectByText(WebElement dropdownElement, String visibleText) {
        try {
            waitForVisible(dropdownElement);
            Select select = new Select(dropdownElement);
            select.selectByVisibleText(visibleText);
            logger.info("Selected text: " + visibleText + " from dropdown: " + dropdownElement);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to select by text: " + visibleText, e);
        }
    }

    protected void scrollToElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to element: " + element);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to scroll to element: " + element, e);
        }
    }
}
