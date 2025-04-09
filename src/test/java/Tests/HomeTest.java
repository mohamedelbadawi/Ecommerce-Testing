package Tests;

import Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {
    protected HomePage homePage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(driver);
    }

    @DataProvider(name = "subscriptionEmail")
    public Object[][] getSubscriptionEmail() {
        return new Object[][]{
                {"m@m.com"}
        };
    }   
    @Test(description = "Test Case 10: Verify Subscription in home page", dataProvider = "subscriptionEmail")
    public void testSubscriptionForm(String email) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.scrollToFooter();
        softAssert.assertTrue(homePage.isSubscriptionTitleDisplayed(), "Subscription title is not displayed");
        homePage.enterSubscriptionEmail("m@m.com").clickSubscribeButton();
        Assert.assertTrue(homePage.isSubscriptionSuccessAlertDisplayed(), "Subscription success alert is not displayed");
    }
}
