package Tests;

import Pages.HomePage;
import Pages.ProductDetailsPage;
import Pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReviewTest extends BaseTest {
    private ProductDetailsPage productDetailsPage;
    private ProductsPage productsPage;
    private HomePage homePage;

    @BeforeMethod
    public void init() {
        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);

    }

    @DataProvider(name = "reviewData")
    public Object[][] reviewData() {
        return new Object[][]{
                {"review@gmail.com", "review name", "review description"},
        };
    }

    @Test(description = "Test Case 21: Add review on product", dataProvider = "reviewData")
    public void AddReviewOnProduct(String email, String name, String description) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickProductsButton();
        softAssert.assertTrue(productsPage.isProductsPage(), "Products page is not visible");
        productsPage.clickFirstProduct();
        productDetailsPage.enterReviewEmail(email).enterReviewName(name).enterReviewDescription(description).clickReviewSubmitButton();
        Assert.assertTrue(productDetailsPage.isReviewSuccessMessageDisplayed(), "Review success message is not displayed");

    }
}
