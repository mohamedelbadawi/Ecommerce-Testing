package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsPage extends BasePage {
    private static final Logger logger = Logger.getLogger(ProductsPage.class.getName());
    private final By productsList = By.className("features_items");
    private final By searchedProductsTitle = By.cssSelector(".features_items .title");
    private final By firstProductViewButton = By.xpath("(//div[@class='col-sm-4']//a[contains(text(),'View Product')])[1]");
    private final By searchInput = By.name("search");
    private final By searchButton = By.id("submit_search");
    private final By searchResult = By.cssSelector(".features_items .single-products");
    private final By searchedProductName = By.cssSelector(".productinfo p");
    private final By singleProduct = By.cssSelector(".features_items .col-sm-4 .single-products");
    private final By singleProductAddToCartButton = By.cssSelector(".product-overlay .add-to-cart");
    private final By continueButton = By.cssSelector("#cartModal .btn-success");
    private final By viewCartButton = By.cssSelector("#cartModal .modal-body a");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductsPage() {
        String pageTitle = getPageTitle();
        boolean isProductsPage = pageTitle.contains("All Products");
        logger.info("Checking if on Products Page: " + isProductsPage);
        return isProductsPage;
    }

    public boolean isProductsListDisplayed() {
        boolean displayed = isElementDisplayed(productsList);
        logger.info("Products list displayed: " + displayed);
        return displayed;
    }

    public void clickFirstProduct() {
        try {
            WebElement element = driver.findElement(firstProductViewButton);
            scrollToElement(element);
            click(element);
            logger.info("Clicked on first product view button");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to click first product view button", e);
        }
    }

    public ProductsPage enterProductName(String productName) {
        try {
            WebElement element = driver.findElement(searchInput);
            scrollToElement(element);
            waitForVisible(element);
            element.sendKeys(productName);
            logger.info("Entered product name: " + productName);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to enter product name", e);
        }
        return this;
    }

    public ProductsPage clickSearchButton() {
        try {
            WebElement element = driver.findElement(searchButton);
            scrollToElement(element);
            click(element);
            logger.info("Clicked search button");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to click search button", e);
        }
        return this;
    }

    public boolean isSearchedProductsTitleDisplayed() {
        boolean displayed = isElementDisplayed(searchedProductsTitle);
        logger.info("Searched products title displayed: " + displayed);
        return displayed;
    }

    public boolean isSearchResultDisplayedAndNotEmpty() {
        if (!isElementDisplayed(searchResult)) {
            logger.warning("Search result is not displayed");
            return false;
        }
        List<WebElement> products = driver.findElements(searchResult);
        logger.info("Search results count: " + products.size());
        return !products.isEmpty();
    }

    public boolean isSearchResultRelatedToSearchedProductName(String productName) {
        List<WebElement> products = driver.findElements(searchResult);
        for (WebElement product : products) {
            scrollToElement(product);
            String searchedProductNameText = product.findElement(searchedProductName).getText().toLowerCase();
            logger.info("Searched product name: " + searchedProductNameText);
            if (!searchedProductNameText.contains(productName.toLowerCase())) {
                logger.warning("Search result does not match product name: " + productName);
                return false;
            }
        }
        return true;
    }


    public void clickViewCartButton() {
        click(driver.findElement(viewCartButton));
    }

    public ProductsPage hoverOverProductsAndAddToCart(Integer productsNumber) {
        List<WebElement> products = driver.findElements(singleProduct);

        productsNumber = Math.min(productsNumber, products.size());

        Actions actions = new Actions(driver);

        for (int i = 0; i < productsNumber; i++) {
            scrollToElement(products.get(i));
            actions.moveToElement(products.get(i)).perform();
            click(products.get(i).findElement(singleProductAddToCartButton));
            if (i != productsNumber - 1) {
                click(driver.findElement(continueButton));
            }
        }
        return this;

    }
}
