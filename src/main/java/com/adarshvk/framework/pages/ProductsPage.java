package com.adarshvk.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page object for the SauceDemo inventory/products page (/inventory.html).
 */
public class ProductsPage {

    private final WebDriver driver;

    private final By pageTitle = By.className("title");
    private final By inventoryItems = By.className("inventory_item");
    private final By addToCartButtons = By.cssSelector("button.btn_inventory");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");
    private final By sortDropdown = By.className("product_sort_container");
    private final By inventoryItemPrice = By.className("inventory_item_price");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoaded() {
        return driver.findElement(pageTitle).getText().equalsIgnoreCase("Products");
    }

    public int getInventoryCount() {
        return driver.findElements(inventoryItems).size();
    }

    public void addFirstNItemsToCart(int n) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        for (int i = 0; i < n && i < buttons.size(); i++) {
            buttons.get(i).click();
        }
    }

    public int getCartCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(badges.get(0).getText());
    }

    public CartPage goToCart() {
        driver.findElement(cartLink).click();
        return new CartPage(driver);
    }

    public void sortBy(String visibleOptionText) {
        driver.findElement(sortDropdown).click();
        driver.findElement(By.xpath("//option[text()='" + visibleOptionText + "']")).click();
    }

    public List<Double> getListedPrices() {
        return driver.findElements(inventoryItemPrice).stream()
                .map(el -> Double.parseDouble(el.getText().replace("$", "")))
                .toList();
    }
}
