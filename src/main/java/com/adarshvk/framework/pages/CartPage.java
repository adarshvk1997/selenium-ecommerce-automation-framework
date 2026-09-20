package com.adarshvk.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object for the SauceDemo cart page (/cart.html).
 */
public class CartPage extends BasePage {

    private final By cartItems = By.className("cart_item");
    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
        waitUntilLoaded(checkoutButton);
    }

    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    public CheckoutPage proceedToCheckout() {
        driver.findElement(checkoutButton).click();
        return new CheckoutPage(driver);
    }

    public ProductsPage continueShopping() {
        driver.findElement(continueShoppingButton).click();
        return new ProductsPage(driver);
    }
}
