package Tests;

import Data.CheckoutData;
import Data.UserLoginData;
import Data.UserRegistrationData;
import Loaders.CheckoutDataLoader;
import Loaders.UserLoginDataLoader;
import Loaders.UserRegistrationDataLoader;
import Pages.*;
import Utils.HelperFunctions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class CheckoutTest extends BaseTest {
    private HomePage homePage;
    private ProductsPage productsPage;
    private CheckoutData checkoutData;
    private LoginPage loginPage;
    private CheckoutPage checkoutPage;
    private ProductDetailsPage productDetailsPage;
    private CartPage cartPage;
    private SignupPage signupPage;
    private boolean isLoggedIn = false;

    @BeforeMethod
    public void init() {
        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        checkoutPage = new CheckoutPage(driver);
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
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

    @Test(description = "Test Case 16: Place Order: Login before Checkout", dataProvider = "loginDataAndCheckoutData")
    public void testLoginBeforeCheckout(UserLoginData user, CheckoutData checkoutData) {
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

    @Test(dataProvider = "userAndCheckoutData")
    public void testPlaceOrderAndRegisterWhileCheckout(UserRegistrationData user, CheckoutData checkoutData) {
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
    }

    @Test(dataProvider = "userAndCheckoutData", description = "Test Case 15: Place Order: Register before Checkout")
    public void testPlaceOrderAndRegisterBeforeCheckout(UserRegistrationData user, CheckoutData checkoutData) {
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

    @Test(description = "Test Case 23: Verify address details in checkout page", dataProvider = "userAndCheckoutData")
    public void testVerifyAddressDetails(UserRegistrationData user, CheckoutData checkoutData) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickLoginRegisterUrl();
        signupPage.fillSignupForm(user);
        homePage.clickProductsButton();
        addToCart();
        cartPage.clickCheckoutButton();
        Assert.assertTrue(checkoutPage.getBillingAddress().equals(user.getAddress1()), "billingAddress is mismatch");
        Assert.assertTrue(checkoutPage.getDeliveryAddress().equals(user.getAddress1()), "deliveryAddress is mismatch");
    }


    @Test(description = "Test Case 24: Download Invoice after purchase order", dataProvider = "userAndCheckoutData")
    public void testDownloadInvoice(UserRegistrationData user, CheckoutData checkoutData) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickLoginRegisterUrl();
        signupPage.fillSignupForm(user);
        isLoggedIn = homePage.isUserLoggedIn();
        softAssert.assertTrue(isLoggedIn, "User is not logged in");
        addToCart();
        homePage.clickCartButton();
        cartPage.clickCheckoutButton();
        checkoutPage.placeOrder(checkoutData).clickDownloadInvoiceButton();

        Assert.assertTrue(HelperFunctions.isInvoiceDownloaded(), "Invoice not downloaded");

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
