package Tests;

import Pages.BrandSectionPage;
import Pages.HomePage;
import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class BrandTest extends BaseTest {
    private BrandSectionPage brandSectionPage;
    private HomePage homePage;

    @BeforeMethod
    public void init() {
        homePage = new HomePage(driver);
        brandSectionPage = new BrandSectionPage(driver);
    }

    @Test(description = "View & Cart Brand Products")
    @Description("This test verifies viewing and carting of brand products from the brand section.")
    @Story("Brand Section Products Viewing")
    @Severity(SeverityLevel.NORMAL)
    public void viewBrandProducts() {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickProductsButton();
        softAssert.assertTrue(brandSectionPage.isBrandSectionDisplayed(), "Brand section is not displayed");

        List<WebElement> brandLinks = brandSectionPage.getBrandsList();
        String brandOneName = brandSectionPage.getBrandName(brandLinks.get(0));
        clickBrandAndVerify(brandLinks.get(0), brandOneName);

        brandLinks = brandSectionPage.getBrandsList();
        String brandTwoName = brandSectionPage.getBrandName(brandLinks.get(1));
        clickBrandAndVerify(brandLinks.get(1), brandTwoName);
    }

    @Step("Click on the brand and verify the product title")
    public void clickBrandAndVerify(WebElement brandLink, String brandName) {
        brandSectionPage.clickBrandLink(brandLink);
        System.out.println(homePage.getProductsTitle() + " " + brandName);
        Assert.assertTrue(homePage.getProductsTitle().contains(brandName), "Product title is not displayed");
    }
}
