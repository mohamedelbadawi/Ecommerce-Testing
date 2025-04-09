package Pages;

import Data.CheckoutData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private final By addressDetails = By.xpath("//div[@class='step-one']/h2[text()='Address Details']");
    private final By reviewYourOrder = By.xpath("//div[@class='step-one']/h2[text()='Review Your Order']");
    private final By commentArea = By.name("message");
    private final By checkoutButton = By.cssSelector("a.check_out");
    private final By cardHolderName = By.name("name_on_card");
    private final By cardNumberInput = By.name("card_number");
    private final By expiryMonthInput = By.name("expiry_month");
    private final By expiryYearInput = By.name("expiry_year");
    private final By cvcInput = By.name("cvc");
    private final By payButton = By.id("submit");
    private final By orderPlacedSuccessfully = By.cssSelector("h2[data-qa='order-placed']");
    private final By deliveryAddress = By.cssSelector("#address_delivery li:nth-child(3)");
    private final By billingAddress = By.cssSelector("#address_invoice li:nth-child(3)");
    private final By downloadInvoiceButton = By.cssSelector(".check_out");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAddressDetailsDisplayed() {
        return isElementDisplayed(addressDetails);
    }

    public boolean isReviewYourOrderDisplayed() {
        return isElementDisplayed(reviewYourOrder);
    }

    public CheckoutPage enterComment(String comment) {
        sendKeys(driver.findElement(commentArea), comment);
        return this;
    }

    public CheckoutPage clickCheckoutButton() {
        click(driver.findElement(checkoutButton));
        return this;
    }

    public CheckoutPage enterCardHolderName(String cardHolder) {
        sendKeys(driver.findElement(cardHolderName), cardHolder);
        return this;
    }

    public CheckoutPage enterCardNumber(String cardNumber) {
        sendKeys(driver.findElement(cardNumberInput), cardNumber);
        return this;
    }

    public CheckoutPage enterExpiryMonth(String month) {
        sendKeys(driver.findElement(expiryMonthInput), month);
        return this;
    }

    public CheckoutPage enterExpiryYear(String year) {
        sendKeys(driver.findElement(expiryYearInput), year);
        return this;
    }

    public CheckoutPage enterCvc(String cvc) {
        sendKeys(driver.findElement(cvcInput), cvc);
        return this;
    }

    public CheckoutPage clickPayButton() {
        click(driver.findElement(payButton));
        return this;
    }

    public boolean isOrderPlacedSuccessfully() {
        return isElementDisplayed(orderPlacedSuccessfully);
    }

    public String getDeliveryAddress() {
        return driver.findElement(deliveryAddress).getText().trim();
    }

    public String getBillingAddress() {
        return driver.findElement(billingAddress).getText().trim();
    }

    public CheckoutPage clickDownloadInvoiceButton() {
        click(driver.findElement(downloadInvoiceButton));
        return this;
    }

    public CheckoutPage placeOrder(CheckoutData checkoutData) {
        return enterComment(checkoutData.getComment())
                .clickCheckoutButton()
                .enterCardHolderName(checkoutData.getCardHolderName())
                .enterCardNumber(checkoutData.getCardNumber())
                .enterExpiryMonth(checkoutData.getExpiryMonth())
                .enterExpiryYear(checkoutData.getExpiryYear())
                .enterCvc(checkoutData.getCvc()).clickPayButton();
    }
}
