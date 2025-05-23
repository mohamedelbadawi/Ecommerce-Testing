package Pages;

import Data.UserRegistrationData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupPage extends BasePage {
    private final By signupTitle = By.xpath("//div[@class='signup-form']//h2");
    private final By signupName = By.cssSelector("[data-qa='signup-name']");
    private final By signupEmail = By.cssSelector("[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("[data-qa='signup-button']");
    private final By informationTitle = By.xpath("//b[contains(text(),'Enter Account Information')]"); // Fixed
    private final By title = By.id("id_gender1");
    private final By passwordInput = By.id("password");
    private final By daysInput = By.id("days");
    private final By monthsInput = By.id("months");
    private final By yearsInput = By.id("years");
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By companyName = By.id("company");
    private final By address1Input = By.id("address1");
    private final By address2Input = By.id("address2");
    private final By countrySelection = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipcode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("[data-qa='create-account']");
    private final By accountCreatedConfirmation = By.cssSelector("[data-qa='account-created']");
    private final By continueButton = By.cssSelector("[data-qa='continue-button']");
    private final By emailAlreadyExistsMessage = By.xpath("//p[contains(text(),'Email Address already exist!')]");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSignupTitleDisplayed() {
        waitForVisible(driver.findElement(signupTitle));
        return driver.findElement(signupTitle).getText().equals("New User Signup!");
    }

    public boolean isEnterAccountInformationTitleDisplayed() {
        waitForVisible(driver.findElement(informationTitle));
        return driver.findElement(informationTitle).isDisplayed();
    }

    public SignupPage enterSignupName(String userName) {
        sendKeys(driver.findElement(signupName), userName);
        return this;
    }

    public SignupPage enterSignupEmail(String email) {
        sendKeys(driver.findElement(signupEmail), email);
        return this;
    }

    public SignupPage clickSignupButton() {
        click(driver.findElement(signupButton));
        return this;
    }

    public SignupPage selectGender() {
        click(driver.findElement(title));
        return this;
    }

    public SignupPage enterPassword(String password) {
        sendKeys(driver.findElement(passwordInput), password);
        return this;
    }

    public SignupPage enterBirthDate(int day, String month, int year) {
        selectByValue(driver.findElement(daysInput), String.valueOf(day));
        selectByText(driver.findElement(monthsInput), month);  // Changed to `selectByText`
        selectByValue(driver.findElement(yearsInput), String.valueOf(year));
        return this;
    }

    public SignupPage enterFirstName(String fname) {
        sendKeys(driver.findElement(firstName), fname);
        return this;
    }

    public SignupPage enterLastName(String lname) {
        sendKeys(driver.findElement(lastName), lname);
        return this;
    }

    public SignupPage enterCompanyName(String company) {
        sendKeys(driver.findElement(companyName), company);
        return this;
    }

    public SignupPage enterAddress(String address1, String address2) {
        sendKeys(driver.findElement(address1Input), address1);
        sendKeys(driver.findElement(address2Input), address2);
        return this;
    }

    public SignupPage selectCountry(String country) {
        selectByText(driver.findElement(countrySelection), country);
        return this;
    }


    public SignupPage enterLocationDetails(String stateName, String cityName, String zip) {
        sendKeys(driver.findElement(state), stateName);
        sendKeys(driver.findElement(city), cityName);
        sendKeys(driver.findElement(zipcode), zip);
        return this;
    }

    public SignupPage enterMobileNumber(String mobile) {
        sendKeys(driver.findElement(mobileNumber), mobile);
        return this;
    }

    public SignupPage clickCreateAccount() {
        scrollToElement(driver.findElement(createAccountButton));
        click(driver.findElement(createAccountButton));
        return this;
    }

    public boolean isAccountCreatedConfirmationDisplayed() {
        return driver.findElement(accountCreatedConfirmation).isDisplayed();
    }

    public SignupPage clickContinueButton() {
        click(driver.findElement(continueButton));
        return this;
    }

    public boolean isAlreadyExistsMessageDisplayed() {
        waitForVisible(driver.findElement(emailAlreadyExistsMessage));
        return driver.findElement(emailAlreadyExistsMessage).isDisplayed();
    }

    public SignupPage fillSignupForm(UserRegistrationData user) {
        return enterSignupName(user.getName())
                .enterSignupEmail(user.getEmail())
                .clickSignupButton()
                .selectGender()
                .enterPassword(user.getPassword())
                .enterBirthDate(user.getBirthDay(), user.getBirthMonth(), user.getBirthYear())
                .enterFirstName(user.getFirstName())
                .enterLastName(user.getLastName())
                .enterCompanyName(user.getCompany())
                .enterAddress(user.getAddress1(), user.getAddress2())
                .selectCountry(user.getCountry())
                .enterLocationDetails(user.getState(), user.getCity(), user.getZipCode())
                .enterMobileNumber(user.getMobileNumber())
                .clickCreateAccount();
    }
}
