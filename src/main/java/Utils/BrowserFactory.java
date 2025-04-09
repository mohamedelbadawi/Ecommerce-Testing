package Utils;

import Config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Objects;

public class BrowserFactory {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = ConfigReader.getBrowser();
            boolean isHeadless = ConfigReader.isHeadless();
            int implicitWait = ConfigReader.getImplicitWait();

            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (isHeadless) {
                        chromeOptions.addArguments("--headless");
                    }
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless) {
                        firefoxOptions.addArguments("--headless");
                    }
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (isHeadless) {
                        edgeOptions.addArguments("--headless");
                    }
                    driver = new EdgeDriver(edgeOptions);
                    break;

            }
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        }
        return driver;
    }

    public static void refreshIfErrorDetected() {
        try {
            String pageSource = Objects.requireNonNull(driver.getPageSource()).toLowerCase();
            String title = Objects.requireNonNull(driver.getTitle()).toLowerCase();

            if (pageSource.contains("error 400") ||
                    pageSource.contains("error 500") ||
                    pageSource.contains("http error") ||
                    pageSource.contains("internal server error") ||
                    pageSource.contains("bad request") ||
                    title.contains("error")) {

                System.out.println("⚠️ HTTP error detected. Refreshing the page...");
                driver.navigate().refresh();
            }
        } catch (Exception e) {
            System.out.println("❌ Error while checking page status: " + e.getMessage());
        }
    }

    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
