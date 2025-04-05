package Tests;

import Pages.HomePage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    private HomePage homePage;
    private LoginPage loginPage;
    private boolean isLoginSuccessful = false;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
    }

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
        return new Object[][]{
                {"loginUser@test.com", "SecurePassword123"}
        };
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][]{
                {"loginUser@test.com", "SecurePassword12"}
        };
    }


    @Test(dataProvider = "validLoginData", description = "Test Case 2: Login User with correct email and password")
    public void testValidLogin(String email, String password) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickLoginRegisterUrl();
        softAssert.assertTrue(loginPage.isLoginTitleDisplayed(), "Login title is not displayed");
        loginPage.enterLoginEmail(email).enterLoginPassword(password).clickLoginButton();
        isLoginSuccessful = homePage.isUserLoggedIn();
        Assert.assertTrue(isLoginSuccessful, "User is not logged in");
    }

    @Test(dataProvider = "invalidLoginData", description = "Login User with incorrect email and password")
    public void testInvalidLogin(String email, String password) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickLoginRegisterUrl();
        softAssert.assertTrue(loginPage.isLoginTitleDisplayed(), "Login title is not displayed");
        loginPage.login(email, password);
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Failed to track error message");
    }

    @Test(dataProvider = "validLoginData", description = "Test Case 4: Logout User")
    public void testValidLogout(String email, String password) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickLoginRegisterUrl();
        softAssert.assertTrue(loginPage.isLoginTitleDisplayed(), "Login title is not displayed");
        loginPage.enterLoginEmail(email).enterLoginPassword(password).clickLoginButton();
        Assert.assertTrue(homePage.isUserLoggedIn(), "User is not logged in");
        homePage.clickLogoutButton();
        Assert.assertTrue(loginPage.isLoginTitleDisplayed(), "Login title is not displayed");
    }

    @AfterMethod
    public void logoutAfterMethod() {
        if (isLoginSuccessful) {
            homePage.clickLogoutButton();
        }
    }

}
