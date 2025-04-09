package Config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop = new Properties();
    private static String environment;

    static {
        try {
            FileInputStream configFile = new FileInputStream("src/test/resources/config.properties");
            prop.load(configFile);
            environment = prop.getProperty("environment");

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    public static String getEnvironment() {
        return environment.trim();
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(prop.getProperty("headless"));
    }

    public static String getPropByEnv(String propName) {
        System.out.println(getEnvironment() + "." + propName);
        return prop.getProperty(getEnvironment() + "." + propName);
    }

    public static String getProp(String propName) {
        return prop.getProperty(propName);
    }

    public static Integer getImplicitWait() {

        return Integer.parseInt(prop.getProperty("implicitWait"));
    }

    public static String getBaseUrl() {
        return getPropByEnv("baseUrl");
//        return "https://www.automationexercise.com";
    }

    public static Integer getExplicitWait() {
        return Integer.parseInt(prop.getProperty("explicitWait"));
    }

    public static String getBrowser() {
        return prop.getProperty("browser");
    }

}
