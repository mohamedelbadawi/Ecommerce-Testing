package Tests;

import Pages.BrandSectionPage;
import Pages.HomePage;
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
    public void viewBrandProducts() {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickProductsButton();
        softAssert.assertTrue(brandSectionPage.isBrandSectionDisplayed(), "Brand section is not displayed");
        List<WebElement> brandLinks = brandSectionPage.getBrandsList();
        String brandOneName = brandSectionPage.getBrandName(brandLinks.get(0));
        brandSectionPage.clickBrandLink(brandLinks.get(0));
        System.out.println(homePage.getProductsTitle() + " " + brandOneName);
        brandLinks = brandSectionPage.getBrandsList();

        Assert.assertTrue(homePage.getProductsTitle().contains(brandOneName), "Product title is not displayed");
        String brandTwoName = brandSectionPage.getBrandName(brandLinks.get(1));
        brandSectionPage.clickBrandLink(brandLinks.get(1));
        System.out.println(homePage.getProductsTitle() + " " + brandTwoName);
        Assert.assertTrue(homePage.getProductsTitle().contains(brandTwoName), "Product title is not displayed");


    }

}
