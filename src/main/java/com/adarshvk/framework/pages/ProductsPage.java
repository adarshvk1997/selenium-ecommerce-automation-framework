package com.adarshvk.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page object for the SauceDemo inventory/products page (/inventory.html).
 */
public class ProductsPage extends BasePage {

    private final By pageTitle = By.className("title");
    private final By inventoryItems = By.className("inventory_item");
    // Both "Add to cart" and "Remove" buttons share the btn_inventory class - btn_primary
    // is what's specific to the not-yet-added state, so this only ever matches buttons
    // this method actually wants to click.
    private final By addToCartButtons = By.cssSelector("button.btn_inventory.btn_primary");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");
    private final By sortDropdown = By.className("product_sort_container");
    private final By inventoryItemPrice = By.className("inventory_item_price");

    public ProductsPage(WebDriver driver) {
        super(driver);
        waitUntilLoaded(pageTitle);
    }

    public boolean isLoaded() {
        return driver.findElement(pageTitle).getText().equalsIgnoreCase("Products");
    }

    public int getInventoryCount() {
        return driver.findElements(inventoryItems).size();
    }

    public void addFirstNItemsToCart(int n) {
        // Re-query before every click rather than capturing the list once: clicking
        // "Add to cart" swaps that button to "Remove" (React re-renders it), which can
        // invalidate WebElement references captured before the click. Confirming the
        // cart badge after each click also means we never move on to the next item
        // before this add has actually registered.
        for (int added = 0; added < n; added++) {
            List<WebElement> remaining = driver.findElements(addToCartButtons);
            if (remaining.isEmpty()) {
                break;
            }
            int expectedCount = added + 1;
            remaining.get(0).click();
            wait.until(webDriver -> getCartCount() == expectedCount);
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
