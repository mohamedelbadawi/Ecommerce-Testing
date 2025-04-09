package Tests;

import Pages.CategorySectionPage;
import Pages.HomePage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CategoryTest extends BaseTest {
    protected HomePage homePage;
    protected CategorySectionPage categorySectionPage;

    @BeforeMethod
    public void init() {
        homePage = new HomePage(driver);
        categorySectionPage = new CategorySectionPage(driver);
    }

    @Test(description = "Test case 18 : test all category and subcategory in home page")
    public void testViewCategoryProducts() {
        Assert.assertTrue(homePage.isCategoryProductsSectionDisplayed(), "Category products section is not displayed");

        // Get all categories
        List<WebElement> categories = categorySectionPage.getAllCategoryPanels();

        for (WebElement category : categories) {
            categorySectionPage.expandCategoryPanel(category);
            String categoryName = categorySectionPage.getCategoryPanelTitle(category);

            // Get subcategories for the category
            List<WebElement> subCategories = categorySectionPage.getAllSubCategoryPanels(category);

            for (WebElement subCategory : subCategories) {
                String subCategoryName = categorySectionPage.getSubCategoryPanelTitle(subCategory);
                // Open subcategory in new tab
                categorySectionPage.clickOnSubCategory(subCategory);

                // Switch to the new tab
                String originalWindow = driver.getWindowHandle();
                homePage.switchToNewTab(originalWindow);

                Assert.assertTrue(homePage.getProductsTitle().contains(categoryName) && homePage.getProductsTitle().contains(subCategoryName), "Category product is not displayed");

                // Close the new tab and switch back to the original tab
                closeCurrentTab();
                switchBackToOriginalWindow(originalWindow);
            }
        }
    }
}

