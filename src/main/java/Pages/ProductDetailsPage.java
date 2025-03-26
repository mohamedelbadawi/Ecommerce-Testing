package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage extends BasePage {
    private final By productName = By.cssSelector(".product-information > h2");
    private final By categoryName = By.cssSelector(".product-information > p:first-of-type");
    private final By price = By.cssSelector(".product-information > span:first-of-type > span:first-of-type");
    private final By availability = By.xpath("//div[contains(@class, 'product-information')]/p[b[contains(text(),'Availability')]]");
    private final By condition = By.xpath("//div[contains(@class, 'product-information')]/p[b[contains(text(),'Condition:')]]");
    private final By brand = By.xpath("//div[contains(@class, 'product-information')]/p[b[contains(text(),'Brand:')]]");

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductNameDisplayed() {
        return isElementDisplayed(productName);
    }

    public boolean isCategoryNameDisplayed() {
        return isElementDisplayed(categoryName);
    }

    public boolean isPriceDisplayed() {
        return isElementDisplayed(price);
    }

    public boolean isAvailabilityDisplayed() {
        return isElementDisplayed(availability);
    }

    public boolean isConditionDisplayed() {
        return isElementDisplayed(condition);
    }

    public boolean isBrandDisplayed() {
        return isElementDisplayed(brand);
    }

}
