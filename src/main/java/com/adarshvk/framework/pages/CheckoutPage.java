package com.adarshvk.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page object covering the SauceDemo checkout flow:
 * checkout-step-one.html (info) -> checkout-step-two.html (overview) -> checkout-complete.html
 *
 * Submitting the info form is async (this is a React app) - the page doesn't change
 * the instant "Continue" is clicked. An explicit wait for the actual outcome (URL
 * change, or the error message appearing) is used instead of assuming either one.
 */
public class CheckoutPage extends BasePage {

    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By completeHeader = By.className("complete-header");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");
    private final By totalLabel = By.className("summary_total_label");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        waitUntilLoaded(firstNameField);
    }

    public CheckoutPage fillShippingInfo(String firstName, String lastName, String postalCode) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
        driver.findElement(continueButton).click();

        // Submitting resolves to exactly one of two outcomes: navigation to step-two,
        // or a validation error rendered in place. Wait for either rather than assuming.
        wait.until(webDriver ->
                webDriver.getCurrentUrl().contains("checkout-step-two")
                        || !webDriver.findElements(errorMessage).isEmpty());
        return this;
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(errorMessage).isEmpty();
    }

    public String getTotalText() {
        return driver.findElement(totalLabel).getText();
    }

    public void finishOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader));
    }

    public boolean isOrderComplete() {
        return driver.findElement(completeHeader).getText().equalsIgnoreCase("Thank you for your order!");
    }
}
