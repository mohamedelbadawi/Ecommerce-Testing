package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CategorySectionPage extends BasePage {
    private final By categoryPanel = By.cssSelector(".panel.panel-default");
    private final By categoryPanelTitle = By.cssSelector(".panel-title a");
    private final By subCategoryTitle = By.cssSelector(".panel-body ul");

    public CategorySectionPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getAllCategoryPanels() {
        return driver.findElements(categoryPanel);
    }

    public CategorySectionPage expandCategoryPanel(WebElement categoryPanel) {
        click(categoryPanel.findElement(categoryPanelTitle));
        return this;
    }

    public String getCategoryPanelTitle(WebElement category) {
        return category.getText().trim();
    }

    public List<WebElement> getAllSubCategoryPanels(WebElement categoryPanel) {
        waitForVisible(categoryPanel.findElement(subCategoryTitle));
        return categoryPanel.findElement(subCategoryTitle).findElements(By.tagName("a"));
    }

    public String getSubCategoryPanelTitle(WebElement subCategory) {
        return subCategory.getText().trim();
    }

    public CategorySectionPage clickOnSubCategory(WebElement subCategory) {
        openLinkInNewTab(subCategory);
        return this;
    }
}
