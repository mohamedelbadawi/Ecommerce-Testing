package Tests;

import Pages.HomePage;
import Pages.ProductDetailsPage;
import Pages.ProductsPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProductTest extends BaseTest {
    private HomePage homePage;
    private ProductsPage productsPage;
    private ProductDetailsPage productDetailsPage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
    }

    @DataProvider(name = "productSearchName")
    public Object[][] productSearchName() {
        return new Object[][]{
                {"men"}
        };
    }

    @Test(description = "Test Case 8: Verify All Products and product detail page")
    @Description("This test case verifies that all products are displayed and the product details page works as expected.")
    @Story("Product Details Page")
    @Severity(SeverityLevel.CRITICAL)
    public void testViewAllProductsAndProductDetails() {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickProductsButton();
        softAssert.assertTrue(productsPage.isProductsPage(), "Products page is not visible");

        Assert.assertTrue(productsPage.isProductsListDisplayed(), "Product list is not displayed");

        productsPage.clickFirstProduct();

        Assert.assertTrue(productDetailsPage.isProductNameDisplayed(), "Product name is not displayed");
        Assert.assertTrue(productDetailsPage.isCategoryNameDisplayed(), "Category name is not displayed");
        Assert.assertTrue(productDetailsPage.isPriceDisplayed(), "Price is not displayed");
        Assert.assertTrue(productDetailsPage.isConditionDisplayed(), "Condition is not displayed");
        Assert.assertTrue(productDetailsPage.isBrandDisplayed(), "Brand is not displayed");
        Assert.assertTrue(productDetailsPage.isAvailabilityDisplayed(), "Availability is not displayed");
    }

    @Test(description = "Test Case 9: Search Product", dataProvider = "productSearchName")
    @Description("This test case verifies that searching for a product works and the results match the searched product name.")
    @Story("Product Search")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchProduct(String productName) {
        softAssert.assertTrue(homePage.isHomePage(), "Home page is not visible");
        homePage.clickProductsButton();
        softAssert.assertTrue(productsPage.isProductsPage(), "Products page is not visible");

        Assert.assertTrue(productsPage.isProductsListDisplayed(), "Product list is not displayed");

        productsPage.enterProductName(productName).clickSearchButton();

        softAssert.assertTrue(productsPage.isSearchedProductsTitleDisplayed(), "Searched product title is not displayed");
        Assert.assertTrue(productsPage.isSearchResultDisplayedAndNotEmpty(), "Search result is not displayed and may be empty");
        Assert.assertTrue(productsPage.isSearchResultRelatedToSearchedProductName(productName), "The searched product name is not related to the search results");
    }
}
