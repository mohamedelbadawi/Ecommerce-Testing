package Tests;

import Data.UserData;
import Loaders.UserDataLoader;
import Pages.HomePage;
import Pages.SignupPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class RegisterTest extends BaseTest {
    private SignupPage signupPage;
    private HomePage homePage;
    private boolean isLoggedIn;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(driver);
        signupPage = new SignupPage(driver);
    }

    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        List<UserData> users = UserDataLoader.loadUsersFromJson();
        return users.stream()
                .map(user -> new Object[]{user})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "userData", description = "Test Case 1: Register User")

    public void testRegisterUser(UserData user) {

        softAssert.assertTrue(homePage.isHomePage(), "Home page is not displayed!");
        // Open register page
        homePage.clickLoginRegisterUrl();
        softAssert.assertTrue(signupPage.isSignupTitleDisplayed(), "Signup page title is missing!");
        // Perform signup steps
        signupPage.fillSignupForm(user);
        Assert.assertTrue(signupPage.isAccountCreatedConfirmationDisplayed(), "Account creation failed");
        signupPage.clickContinueButton();
        isLoggedIn = homePage.isUserLoggedIn();
        Assert.assertTrue(isLoggedIn, "User is not logged in!");
    }

    @DataProvider(name = "AlreadyRegisteredUserData")
    public Object[][] getAlreadyRegisteredUserData() {
        return new Object[][]{
                {"Mohamed", "loginUser@test.com"}

        };
    }

    @Test(dataProvider = "AlreadyRegisteredUserData", description = "Test Case 5: Register User with existing email")
    public void testRegisterUserWithExistingEmail(String signupName, String signupEmail) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not displayed!");
        homePage.clickLoginRegisterUrl();
        softAssert.assertTrue(signupPage.isSignupTitleDisplayed(), "Signup page title is missing!");
        signupPage.enterSignupEmail(signupEmail).enterSignupName(signupName).clickSignupButton();
        Assert.assertTrue(signupPage.isAlreadyExistsMessageDisplayed(), "AlreadyExists message not displayed!");
    }

    @Test(dataProvider = "userData")
    protected void test(UserData user) throws IOException {
        System.out.println(user.getEmail());
    }


    @AfterMethod
    public void deleteAccount() {
        if (isLoggedIn) {
            homePage.clickDeleteAccountButton();
        }
    }
}
