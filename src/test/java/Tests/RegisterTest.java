package Tests;

import Data.UserRegistrationData;
import Loaders.UserRegistrationDataLoader;
import Pages.HomePage;
import Pages.SignupPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

@Epic("User Registration")
@Feature("Signup Process")
public class RegisterTest extends BaseTest {
    private SignupPage signupPage;
    private HomePage homePage;
    private boolean isLoggedIn = false;

    @BeforeMethod
    @Step("Setup before each test")
    public void setup() {
        homePage = new HomePage(driver);
        signupPage = new SignupPage(driver);
        isLoggedIn = false;
    }

    @DataProvider(name = "userData")
    @Step("Provide user data from JSON")
    public Object[][] getUserData() {
        List<UserRegistrationData> users = UserRegistrationDataLoader.loadUsersFromJson();
        return users.stream()
                .map(user -> new Object[]{user})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "userData", description = "Test Case 1: Register User")
    @Story("Valid user registration")
    @Description("Ensure that a user can register successfully using valid data")
    @Severity(SeverityLevel.CRITICAL)
    public void testRegisterUser(UserRegistrationData user) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not displayed!");
        homePage.clickLoginRegisterUrl();
        softAssert.assertTrue(signupPage.isSignupTitleDisplayed(), "Signup page title is missing!");
        signupPage.fillSignupForm(user);
        Assert.assertTrue(signupPage.isAccountCreatedConfirmationDisplayed(), "Account creation failed");
        signupPage.clickContinueButton();
        isLoggedIn = homePage.isUserLoggedIn();
        Assert.assertTrue(isLoggedIn, "User is not logged in!");
    }

    @DataProvider(name = "AlreadyRegisteredUserData")
    @Step("Provide already registered user data")
    public Object[][] getAlreadyRegisteredUserData() {
        return new Object[][]{
                {"Mohamed", "YKlI4bdkA3@gmail.com"}
        };
    }

    @Test(dataProvider = "AlreadyRegisteredUserData", description = "Test Case 5: Register User with existing email")
    @Story("User registration with duplicate email")
    @Description("Check that attempting to register with an already used email address shows appropriate message")
    @Severity(SeverityLevel.NORMAL)
    public void testRegisterUserWithExistingEmail(String signupName, String signupEmail) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not displayed!");
        homePage.clickLoginRegisterUrl();
        softAssert.assertTrue(signupPage.isSignupTitleDisplayed(), "Signup page title is missing!");
        signupPage.enterSignupEmail(signupEmail).enterSignupName(signupName).clickSignupButton();
        Assert.assertTrue(signupPage.isAlreadyExistsMessageDisplayed(), "AlreadyExists message not displayed!");
    }

    @Test(dataProvider = "userData")
    @Story("Debug user data")
    @Description("Print user data to validate DataProvider")
    @Severity(SeverityLevel.MINOR)
    protected void test(UserRegistrationData user) throws IOException {
        System.out.println(user.getEmail());
    }

    @AfterMethod
    @Step("Clean up by deleting user account if logged in")
    public void deleteAccount() {
        if (isLoggedIn) {
            homePage.clickDeleteAccountButton();
        }
    }
}
