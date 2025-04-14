package Utils.listeners;

import Utils.BrowserFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotListener implements ITestListener {


    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = BrowserFactory.getDriver();
        if (driver == null) {
            System.out.println("Driver is null, cannot take screenshot.");
            return;
        }
        File screenshotDir = new File("screenshots");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs(); // Creates the directory if missing
        }

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "screenshots/" + timeStamp + "_" + result.getName() + ".png";

        try {
            Files.copy(screenshot.toPath(), Path.of(fileName), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot taken: " + fileName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save screenshot", e);
        }
    }
}
