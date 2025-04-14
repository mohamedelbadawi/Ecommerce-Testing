package Pages;

import Utils.HelperFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {
    private final By singleCartProduct = By.cssSelector("tr[id*=\"product\"]");
    private final By productPrice = By.cssSelector(".cart_price");
    private final By productQuantity = By.cssSelector(".cart_quantity");
    private final By productTotalPrice = By.cssSelector(".cart_total_price");
    private final By checkoutButton = By.cssSelector(".check_out");
    private final By loginButton = By.cssSelector("#checkoutModal a");
    private final By cartProductDeleteButton = By.cssSelector(".cart_quantity_delete");
    private final By cartIsEmpty = By.id("empty_cart");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getCart() {
        return driver.findElements(singleCartProduct);
    }

    public boolean isCartProductsEqual(Integer count) {
        return getCart().size() == count;
    }

    public boolean isCartProductsQuantityCorrect(Integer expectedQuantity) {
        List<WebElement> products = getCart();
        for (WebElement product : products) {
            double quantity = HelperFunctions.getNumberFromString(product.findElement(productQuantity).getText());
            if (quantity != expectedQuantity) {
                return false;
            }
        }
        return true;
    }

    public boolean isCartProductsPriceCorrect(List<WebElement> products) {
        for (WebElement product : products) {
            double price = HelperFunctions.getNumberFromString(product.findElement(productPrice).getText());
            double quantity = HelperFunctions.getNumberFromString(product.findElement(productQuantity).getText());
            double expectedTotalPrice = price * quantity;
            double actualTotalPrice = HelperFunctions.getNumberFromString(product.findElement(productTotalPrice).getText());
            if (expectedTotalPrice != actualTotalPrice) {
                return false;
            }
        }
        return true;
    }


    public boolean isCartProductsQuantityAndPrice() {
        List<WebElement> products = getCart();
        return isCartProductsPriceCorrect(products);
    }

    public CartPage clickCheckoutButton() {
        click(driver.findElement(checkoutButton));
        return this;
    }

    public CartPage removeCartFirstProduct() {
        List<WebElement> cartProducts = getCart();
        click(cartProducts.getFirst().findElement(cartProductDeleteButton));
        return this;
    }

    public CartPage clickLoginButton() {
        click(driver.findElement(loginButton));
        return this;
    }

    public CartPage clickCartProductDeleteButton() {
        click(driver.findElement(cartProductDeleteButton));
        return this;
    }

    public boolean isCartEmpty() {
        return isElementDisplayed(cartIsEmpty);
    }

    public void clearCart() {
        List<WebElement> cartProducts = getCart();
        for (WebElement product : cartProducts) {
            click(product.findElement(cartProductDeleteButton));
        }
    }
}