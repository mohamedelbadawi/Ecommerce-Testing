package Tests;


import Config.ConfigReader;
import Utils.BrowserFactory;
import Utils.listeners.LoggingListener;
import Utils.listeners.ScreenshotListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

@Listeners({LoggingListener.class, ScreenshotListener.class})
public class BaseTest {
    protected WebDriver driver;
    protected SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void setUp() {
        this.driver = BrowserFactory.getDriver();
        driver.get(ConfigReader.getBaseUrl());

    }

    @AfterMethod
    public void tearDown() {
        BrowserFactory.quit();
    }
}
