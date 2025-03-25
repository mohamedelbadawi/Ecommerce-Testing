package Tests;

import Pages.ContactUsPage;
import Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ContactUsTest extends BaseTest {
    private ContactUsPage contactUsPage;
    private HomePage homePage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(driver);
        contactUsPage = new ContactUsPage(driver);
    }

    @DataProvider(name = "contactUsData")
    public Object[][] contactUsData() {
        return new Object[][]{
                {"m@m.com", "test", "test contact us form", "message", "D:\\testing\\automation\\e-commerce\\images\\Test-Logo.svg.png"}
        };
    }

    @Test(description = "Test Case 6: Contact Us Form", dataProvider = "contactUsData")
    public void contactUsTest(String email, String name, String subject, String message, String filePath) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickContactUsButton();
        softAssert.assertTrue(contactUsPage.isGetInTouchMessageDisplayed(), "Contact Us Page is not displayed");
        contactUsPage.enterEmail(email).enterName(name).enterSubject(subject).enterMessage(message).enterFile(filePath).clickSubmitButton().acceptConfirmationAlert();
        Assert.assertTrue(contactUsPage.isSuccessMessageDisplayed(), "Contact Us Page is not displayed");

    }
}
