package Tests;

import Data.CheckoutData;
import Data.UserLoginData;
import Data.UserRegistrationData;
import Loaders.CheckoutDataLoader;
import Loaders.UserLoginDataLoader;
import Loaders.UserRegistrationDataLoader;
import Pages.*;
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
    private boolean isLoggedIn;

    @BeforeMethod
    public void init() {
        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        signupPage = new SignupPage(driver);
        checkoutPage = new CheckoutPage(driver);
        loginPage = new LoginPage(driver);
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

    @DataProvider(name = "loginDataAndCheckoutData")
    public Object[][] getLoginDataAndCheckout() {
        List<UserLoginData> users = UserLoginDataLoader.loadLoginDataFromJson();
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
    public void testAddProductsToCart() {
        homePage.clickProductsButton();
        productsPage.hoverOverProductsAndAddToCart(3).clickViewCartButton();
        Assert.assertTrue(cartPage.isCartProductsEqual(3), "Cart products not equal Added");
        Assert.assertTrue(cartPage.isCartProductsQuantityAndPrice(), "Cart Product Quantity And Price not equal");
    }

    @Test(description = "Test Case 13: Verify Product quantity in Cart")
    public void testProductQuantityInCart() {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickFirstProductViewButton();
        softAssert.assertTrue(productDetailsPage.isProductDetailsPageDisplayed(), "Product details page is not displayed");
        productDetailsPage.setQuantity("4").addToCart().viewCart();
        Assert.assertTrue(cartPage.isCartProductsQuantityCorrect(4), "Cart products not equal Added");
    }

    @Test(dataProvider = "userAndCheckoutData")
    public void TestPlaceOrderAndRegisterWhileCheckout(UserRegistrationData user, CheckoutData checkoutData) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        // Add product to cart
        addToCart();
        // Proceed to checkout and login
        cartPage.clickCheckoutButton().clickLoginButton();
        softAssert.assertTrue(signupPage.isSignupTitleDisplayed(), "Signup page title is missing!");
        // user registration
        signupPage.fillSignupForm(user);
        isLoggedIn = homePage.isUserLoggedIn();
        softAssert.assertTrue(isLoggedIn, "User is not logged in");
//        Navigate back to cart and continue checkout
        homePage.clickCartButton();
        cartPage.clickCheckoutButton();
        softAssert.assertTrue(checkoutPage.isAddressDetailsDisplayed(), "Address details page is not displayed");
        softAssert.assertTrue(checkoutPage.isReviewYourOrderDisplayed(), "Review section is not displayed");
        checkoutPage.placeOrder(checkoutData);
        Assert.assertTrue(checkoutPage.isOrderPlacedSuccessfully(), "Order not placed successfully");
//        softAssert.assertAll();
    }

    @Test(dataProvider = "userAndCheckoutData", description = "Test Case 15: Place Order: Register before Checkout")
    public void TestPlaceOrderAndRegisterBeforeCheckout(UserRegistrationData user, CheckoutData checkoutData) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickLoginRegisterUrl();
        signupPage.fillSignupForm(user);
        isLoggedIn = homePage.isUserLoggedIn();
        softAssert.assertTrue(isLoggedIn, "User is not logged in");
        addToCart();
        homePage.clickCartButton();
        cartPage.clickCheckoutButton();
        checkoutPage.placeOrder(checkoutData);
        Assert.assertTrue(checkoutPage.isOrderPlacedSuccessfully(), "Order not placed successfully");
    }

    @Test(description = "Test Case 16: Place Order: Login before Checkout", dataProvider = "loginDataAndCheckoutData")
    public void TestLoginBeforeCheckout(UserLoginData user, CheckoutData checkoutData) {
        homePage.clickLoginRegisterUrl();
        loginPage.login(user.getEmail(), user.getPassword());
        addToCart();
        homePage.clickCartButton();
        cartPage.clickCheckoutButton();
        softAssert.assertTrue(checkoutPage.isAddressDetailsDisplayed(), "Address details page is not displayed");
        softAssert.assertTrue(checkoutPage.isReviewYourOrderDisplayed(), "Review section is not displayed");
        checkoutPage.placeOrder(checkoutData);
        Assert.assertTrue(checkoutPage.isOrderPlacedSuccessfully(), "Order not placed successfully");
    }


    @Test(description = "Test Case 17: Remove Products From Cart")
    public void testRemoveProductsFromCart() {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        addToCart();
        homePage.clickCartButton();
        cartPage.removeCartFirstProduct();
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart is not empty and the product not removed");
    }

    private void addToCart() {
        productsPage.clickFirstProduct();
        productDetailsPage.setQuantity("1").addToCart().viewCart();
    }


    @AfterMethod
    public void deleteAccount() {
        if (isLoggedIn) {
            homePage.clickDeleteAccountButton();
        }
    }

}
