package Tests;

import Data.CheckoutData;
import Data.UserRegistrationData;
import Loaders.CheckoutDataLoader;
import Loaders.UserRegistrationDataLoader;
import Pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {
    protected HomePage homePage;
    protected ProductsPage productsPage;
    protected CartPage cartPage;
    protected ProductDetailsPage productDetailsPage;
    protected SignupPage signupPage;
    protected CheckoutPage checkoutPage;
    protected LoginPage loginPage;
    private boolean isLoggedIn = false;

    @BeforeMethod
    public void init() {
        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        signupPage = new SignupPage(driver);
        checkoutPage = new CheckoutPage(driver);
        loginPage = new LoginPage(driver);
        isLoggedIn = false;
    }

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
        return new Object[][]{
                {"YKlI4bdkA3@gmail.com", "SecurePassword123"}
        };
    }

    @DataProvider(name = "userAndCheckoutData")
    public Object[][] getUserAndCheckout() {
        List<UserRegistrationData> users = UserRegistrationDataLoader.loadUsersFromJson();
        List<CheckoutData> checkoutData = CheckoutDataLoader.loadPaymentDataFromJson();
        int size = Math.min(users.size(), checkoutData.size());
        Object[][] data = new Object[size][2];
        for (int i = 0; i < size; i++) {
            data[i][0] = users.get(i);
            data[i][1] = checkoutData.get(i);
        }

        return data;
    }

    @Test(description = "Test Case 12: Add Products in Cart")
    @Description("This test case verifies that products can be added to the cart.")
    @Story("Adding Products to Cart")
    @Severity(SeverityLevel.NORMAL)
    public void testAddProductsToCart() {
        homePage.clickProductsButton();
        productsPage.hoverOverProductsAndAddToCart(3).clickViewCartButton();
        Assert.assertTrue(cartPage.isCartProductsEqual(3), "Cart products not equal Added");
        Assert.assertTrue(cartPage.isCartProductsQuantityAndPrice(), "Cart Product Quantity And Price not equal");
    }

    @Test(description = "Test Case 13: Verify Product quantity in Cart")
    @Description("This test case verifies the quantity of a product in the cart.")
    @Story("Verifying Product Quantity in Cart")
    @Severity(SeverityLevel.NORMAL)
    public void testProductQuantityInCart() {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickFirstProductViewButton();
        softAssert.assertTrue(productDetailsPage.isProductDetailsPageDisplayed(), "Product details page is not displayed");
        productDetailsPage.setQuantity("4").addToCart().viewCart();
        Assert.assertTrue(cartPage.isCartProductsQuantityCorrect(4), "Cart products not equal Added");
    }

    @Test(description = "Test Case 17: Remove Products From Cart")
    @Description("This test case verifies that products can be removed from the cart.")
    @Story("Removing Products from Cart")
    @Severity(SeverityLevel.NORMAL)
    public void testRemoveProductsFromCart() {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        addToCart();
        homePage.clickCartButton();
        cartPage.removeCartFirstProduct();
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart is not empty and the product not removed");
    }

    @Test(description = "Test Case 20: Search Products and Verify Cart After Login", dataProvider = "validLoginData")
    @Description("This test case verifies the search functionality and cart after login.")
    @Story("Search Products and Verify Cart After Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testSearchProductsAndVerifyCart(String email, String password) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickProductsButton();
        softAssert.assertTrue(productsPage.isProductsPage(), "Products page is not displayed");
        productsPage.enterProductName("men").clickSearchButton();
        Assert.assertTrue(productsPage.isSearchResultRelatedToSearchedProductName("men"), "Search result related to product name is not displayed");
        addToCart();
        softAssert.assertTrue(cartPage.isCartProductsEqual(1), "Cart products not equal Added");
        homePage.clickLoginRegisterUrl();
        loginPage.login(email, password);
        isLoggedIn = homePage.isUserLoggedIn();
        homePage.clickCartButton();
        Assert.assertFalse(cartPage.isCartEmpty(), "Cart is not empty and the product not removed");
    }

    @Test
    @Description("This test case adds a product from the recommended section to the cart.")
    @Story("Add to Cart from Recommended Items")
    @Severity(SeverityLevel.NORMAL)
    public void addToCartFromRecommendedItems() {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        softAssert.assertTrue(homePage.isRecommendedProductsSectionDisplayed(), "Recommended products section is not displayed");
        homePage.clickAddToCartButtonForRecommendedProducts().clickViewCartViaModal();
        Assert.assertFalse(cartPage.isCartEmpty(), "Cart is not empty and the product not removed");
    }

    @Step("Add a product to the cart and view the cart")
    private void addToCart() {
        productsPage.clickFirstProduct();
        productDetailsPage.setQuantity("1").addToCart().viewCart();
    }

    @AfterMethod
    public void clearCartProducts() {
        if (!cartPage.isCartEmpty()) {
            cartPage.clearCart();
        }
        if (isLoggedIn) {
            homePage.clickLogoutButton();
        }

    }
}
