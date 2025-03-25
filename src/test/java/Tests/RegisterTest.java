package Tests;

import Pages.HomePage;
import Pages.LoginPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void goToLoginPage() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    @Test
    public void testRegisterUser() {
        //verify home page
        softAssert.assertTrue(homePage.isHomePage());
        //open register page
        homePage.clickLoginRegisterUrl();
        //verify signup form
        softAssert.assertTrue(loginPage.isSignupTitleDisplayed());


    }


}
