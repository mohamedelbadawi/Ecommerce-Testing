package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BrandSectionPage extends BasePage {
    private final By brandSection = By.className("brands_products");
    private final By brandLink = By.cssSelector(".brands-name ul li a");
    private final By brandProductsCount = By.cssSelector(".brands-name ul li a span");
    public BrandSectionPage(WebDriver driver) {
        super(driver);
    }

    public boolean isBrandSectionDisplayed() {
        return isElementDisplayed(brandSection);
    }

    public List<WebElement> getBrandsList() {
        return driver.findElement(brandSection).findElements(brandLink);
    }

    public void clickBrandLink(WebElement element) {
        click(element);
    }
    public String getBrandLinkNameText() {
        return driver.findElement(brandLink).getText().trim();
    }
    public String getBrandName(WebElement element) {
        String count = element.findElement(brandProductsCount).getText().trim();
        return element.getText().replace(count, "").trim();
    }
}
