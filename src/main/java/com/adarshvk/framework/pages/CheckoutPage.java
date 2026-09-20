package com.adarshvk.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object covering the SauceDemo checkout flow:
 * checkout-step-one.html (info) -> checkout-step-two.html (overview) -> checkout-complete.html
 */
public class CheckoutPage {

    private final WebDriver driver;

    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By completeHeader = By.className("complete-header");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");
    private final By totalLabel = By.className("summary_total_label");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public CheckoutPage fillShippingInfo(String firstName, String lastName, String postalCode) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
        driver.findElement(continueButton).click();
        return this;
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(errorMessage).isEmpty();
    }

    public String getTotalText() {
        return driver.findElement(totalLabel).getText();
    }

    public void finishOrder() {
        driver.findElement(finishButton).click();
    }

    public boolean isOrderComplete() {
        return driver.findElement(completeHeader).getText().equalsIgnoreCase("Thank you for your order!");
    }
}
