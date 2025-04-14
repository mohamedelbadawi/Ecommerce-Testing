package Tests;

import Data.CheckoutData;
import Data.UserLoginData;
import Data.UserRegistrationData;
import Loaders.CheckoutDataLoader;
import Loaders.UserLoginDataLoader;
import Loaders.UserRegistrationDataLoader;
import Pages.*;
import Utils.HelperFunctions;
import io.qameta.allure.*;
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
    private boolean isAccountDeleted = false;

    @BeforeMethod
    public void init() {
        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        checkoutPage = new CheckoutPage(driver);
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
        isLoggedIn = false;
        isAccountDeleted = false;
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
        assert users != null;
        assert checkoutData != null;
        int size = Math.min(users.size(), checkoutData.size());
        Object[][] data = new Object[size][2];
        for (int i = 0; i < size; i++) {
            data[i][0] = users.get(i);
            data[i][1] = checkoutData.get(i);
        }
        return data;
    }

    @Test(description = "Test Case 16: Place Order: Login before Checkout", dataProvider = "loginDataAndCheckoutData")
    @Feature("Checkout Process")
    @Story("User places an order after logging in")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginBeforeCheckout(UserLoginData user, CheckoutData checkoutData) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");

        login(user);
        isLoggedIn = true;

        addToCart();

        homePage.clickCartButton();
        cartPage.clickCheckoutButton();

        softAssert.assertTrue(checkoutPage.isAddressDetailsDisplayed(), "Address details page is not displayed");
        softAssert.assertTrue(checkoutPage.isReviewYourOrderDisplayed(), "Review section is not displayed");

        checkoutPage.placeOrder(checkoutData);

        Assert.assertTrue(checkoutPage.isOrderPlacedSuccessfully(), "Order not placed successfully");
        logoutAndMarkDeleted();
    }

    @Test(dataProvider = "userAndCheckoutData")
    @Severity(SeverityLevel.NORMAL)
    @Story("User places an order after registration during checkout")
    @Feature("Checkout Process")
    public void testPlaceOrderAndRegisterWhileCheckout(UserRegistrationData user, CheckoutData checkoutData) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        addToCart();
        cartPage.clickCheckoutButton().clickLoginButton();
        softAssert.assertTrue(signupPage.isSignupTitleDisplayed(), "Signup page title is missing!");
        signupPage.fillSignupForm(user).clickContinueButton();
        isLoggedIn = homePage.isUserLoggedIn();
        softAssert.assertTrue(isLoggedIn, "User is not logged in");
        homePage.clickCartButton();
        cartPage.clickCheckoutButton();
        softAssert.assertTrue(checkoutPage.isAddressDetailsDisplayed(), "Address details page is not displayed");
        softAssert.assertTrue(checkoutPage.isReviewYourOrderDisplayed(), "Review section is not displayed");
        checkoutPage.placeOrder(checkoutData);
        Assert.assertTrue(checkoutPage.isOrderPlacedSuccessfully(), "Order not placed successfully");
        logoutAndMarkDeleted();
    }

    @Test(dataProvider = "userAndCheckoutData", description = "Test Case 15: Place Order: Register before Checkout")
    @Severity(SeverityLevel.NORMAL)
    @Story("User places an order after registration before checkout")
    @Feature("Checkout Process")
    public void testPlaceOrderAndRegisterBeforeCheckout(UserRegistrationData user, CheckoutData checkoutData) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickLoginRegisterUrl();
        signupPage.fillSignupForm(user).clickContinueButton();
        isLoggedIn = homePage.isUserLoggedIn();
        softAssert.assertTrue(isLoggedIn, "User is not logged in");
        addToCart();
        homePage.clickCartButton();
        cartPage.clickCheckoutButton();
        checkoutPage.placeOrder(checkoutData);
        Assert.assertTrue(checkoutPage.isOrderPlacedSuccessfully(), "Order not placed successfully");
    }

    @Test(description = "Test Case 23: Verify address details in checkout page", dataProvider = "userAndCheckoutData")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Checkout Process")
    @Story("User verifies address details during checkout")
    public void testVerifyAddressDetails(UserRegistrationData user, CheckoutData checkoutData) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickLoginRegisterUrl();
        signupPage.fillSignupForm(user).clickContinueButton();
        isLoggedIn = homePage.isUserLoggedIn();
        homePage.clickProductsButton();
        addToCart();
        cartPage.clickCheckoutButton();
        Assert.assertEquals(checkoutPage.getBillingAddress(), user.getAddress1(), "billingAddress is mismatch");
        System.out.println(checkoutPage.getBillingAddress() + " " + user.getAddress1());
        Assert.assertEquals(checkoutPage.getDeliveryAddress(), user.getAddress1(), "deliveryAddress is mismatch");
        System.out.println(checkoutPage.getDeliveryAddress() + " " + user.getAddress1());
    }

    @Test(description = "Test Case 24: Download Invoice after purchase order", dataProvider = "userAndCheckoutData")
    @Severity(SeverityLevel.MINOR)
    @Feature("Checkout Process")
    @Story("User downloads invoice after purchase")
    public void testDownloadInvoice(UserRegistrationData user, CheckoutData checkoutData) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickLoginRegisterUrl();
        signupPage.fillSignupForm(user).clickContinueButton();
        isLoggedIn = homePage.isUserLoggedIn();
        softAssert.assertTrue(isLoggedIn, "User is not logged in");
        addToCart();
        homePage.clickCartButton();
        cartPage.clickCheckoutButton();
        checkoutPage.placeOrder(checkoutData).clickDownloadInvoiceButton();
        Assert.assertTrue(HelperFunctions.isInvoiceDownloaded(), "Invoice not downloaded");
        logoutAndMarkDeleted();
    }

    @Step("Logging in with email: {0}")
    public void login(UserLoginData user) {
        homePage.clickLoginRegisterUrl();
        loginPage.login(user.getEmail(), user.getPassword());
    }

    @Step("Adding product to the cart")
    private void addToCart() {
        productsPage.clickFirstProduct();
        productDetailsPage.setQuantity("1").addToCart().viewCart();
    }

    @Step("Logging out and marking account for deletion")
    private void logoutAndMarkDeleted() {
        homePage.clickLogoutButton();
        isLoggedIn = false;
        isAccountDeleted = true;
    }


    @AfterMethod
    public void deleteAccount() {
        if (isLoggedIn && !isAccountDeleted) {
            homePage.clickDeleteAccountButton();
        }
    }
}
