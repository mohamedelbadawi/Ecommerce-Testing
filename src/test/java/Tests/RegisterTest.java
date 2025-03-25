package Tests;

import Pages.HomePage;
import Pages.SignupPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
        return new Object[][]{
                {"John Doe", "loginUser32@test.com", "Male", "SecurePassword123", 15, "March", 1995, "John", "Doe", "TechCorp", "123 Main St", "Apt 4B", "United States", "California", "Los Angeles", "90001", "1234567890"},
                {"Alice Smith", "alidc2sdse.@test.com", "Female", "AlicePass456", 25, "July", 1992, "Alice", "Smith", "HealthCo", "456 Elm St", "Suite 10", "Canada", "Ontario", "Toronto", "M5G1Z4", "9876543210"}
        };
    }

    @Test(dataProvider = "userData", description = "Test Case 1: Register User")

    public void testRegisterUser(String signupName, String signupEmail, String gender, String password,
                                 Integer birthDay, String birthMonth, Integer birthYear,  // FIXED HERE
                                 String firstName, String lastName, String company,
                                 String address1, String address2, String country,
                                 String state, String city, String zipCode, String mobileNumber) {

        softAssert.assertTrue(homePage.isHomePage(), "Home page is not displayed!");

        // Open register page
        homePage.clickLoginRegisterUrl();
        softAssert.assertTrue(signupPage.isSignupTitleDisplayed(), "Signup page title is missing!");

        // Perform signup steps
        signupPage.enterSignupName(signupName)
                .enterSignupEmail(signupEmail)
                .clickSignupButton()
                .selectGender()
                .enterPassword(password)
                .enterBirthDate(birthDay, birthMonth, birthYear)
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterCompanyName(company)
                .enterAddress(address1, address2)
                .selectCountry(country)
                .enterLocationDetails(state, city, zipCode)
                .enterMobileNumber(mobileNumber)
                .clickCreateAccount();
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


    @AfterMethod
    public void deleteAccount() {
        if (isLoggedIn) {
            homePage.clickDeleteAccountButton();
        }
    }
}
