package com.adarshvk.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page object for the SauceDemo login page (/).
 */
public class LoginPage {

    private final WebDriver driver;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public ProductsPage loginAs(String username, String password) {
        WebElement username_ = driver.findElement(usernameField);
        username_.clear();
        username_.sendKeys(username);

        WebElement password_ = driver.findElement(passwordField);
        password_.clear();
        password_.sendKeys(password);

        driver.findElement(loginButton).click();
        return new ProductsPage(driver);
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(errorMessage).isEmpty();
    }

    public String getErrorText() {
        return driver.findElement(errorMessage).getText();
    }
}
