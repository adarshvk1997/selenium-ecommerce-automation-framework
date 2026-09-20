package com.adarshvk.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page object for the SauceDemo login page (/).
 */
public class LoginPage extends BasePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public ProductsPage loginAs(String username, String password) {
        WebElement username_ = driver.findElement(usernameField);
        username_.clear();
        username_.sendKeys(username);

        WebElement password_ = driver.findElement(passwordField);
        password_.clear();
        password_.sendKeys(password);

        driver.findElement(loginButton).click();

        // Valid credentials navigate to Products; invalid ones stay here with an error.
        // Wait for whichever actually happens, then only construct a ProductsPage if
        // that's genuinely where we ended up - its constructor waits for a Products-page
        // element, which would never appear (and time out) after a rejected login.
        wait.until(webDriver ->
                !webDriver.findElements(By.className("inventory_list")).isEmpty()
                        || !webDriver.findElements(errorMessage).isEmpty());

        if (isErrorDisplayed()) {
            return null;
        }
        return new ProductsPage(driver);
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(errorMessage).isEmpty();
    }

    public String getErrorText() {
        return driver.findElement(errorMessage).getText();
    }
}
