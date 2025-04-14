package Tests;

import Pages.HomePage;
import io.qameta.allure.*;
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
    @Description("This test case verifies the subscription functionality on the home page, including entering an email and confirming the subscription success alert.")
    @Story("Home Page Subscription")
    @Severity(SeverityLevel.NORMAL)
    public void testSubscriptionForm(String email) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.scrollToFooter();
        softAssert.assertTrue(homePage.isSubscriptionTitleDisplayed(), "Subscription title is not displayed");

        homePage.enterSubscriptionEmail(email).clickSubscribeButton();

        Assert.assertTrue(homePage.isSubscriptionSuccessAlertDisplayed(), "Subscription success alert is not displayed");
    }
}
