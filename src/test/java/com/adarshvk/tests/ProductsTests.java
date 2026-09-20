package com.adarshvk.tests;

import com.adarshvk.framework.base.BaseTest;
import com.adarshvk.framework.pages.CartPage;
import com.adarshvk.framework.pages.LoginPage;
import com.adarshvk.framework.pages.ProductsPage;
import com.adarshvk.framework.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Functional coverage for browsing, sorting, and adding products to the cart.
 * Depends on a valid login, done in @BeforeMethod so each test starts from
 * the Products page rather than repeating the login flow inline.
 */
public class ProductsTests extends BaseTest {

    private ProductsPage productsPage;

    // Runs after BaseTest#setUp (TestNG runs superclass @BeforeMethod methods first),
    // so every test in this class starts already logged in on the Products page.
    @BeforeMethod
    public void loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        productsPage = loginPage.loginAs(
                ConfigReader.get("standard.username"),
                ConfigReader.get("valid.password"));
    }

    @Test(description = "Products page should list all 6 SauceDemo inventory items")
    public void productsPage_listsAllInventoryItems() {
        Assert.assertEquals(productsPage.getInventoryCount(), 6,
                "Expected 6 inventory items on the Products page");
    }

    @Test(description = "Adding items to cart should update the cart badge count")
    public void addingItemsToCart_updatesCartBadge() {
        productsPage.addFirstNItemsToCart(2);
        Assert.assertEquals(productsPage.getCartCount(), 2,
                "Cart badge did not reflect the number of items added");
    }

    @Test(description = "Cart page should show the same items added from the Products page")
    public void cartPage_reflectsItemsAddedFromProductsPage() {
        productsPage.addFirstNItemsToCart(3);
        CartPage cartPage = productsPage.goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 3,
                "Cart page item count did not match items added");
    }

    @Test(description = "Sorting price low to high should return an ascending price list")
    public void sortByPriceLowToHigh_returnsAscendingPrices() {
        productsPage.sortBy("Price (low to high)");
        List<Double> prices = productsPage.getListedPrices();

        List<Double> sorted = prices.stream().sorted().toList();
        Assert.assertEquals(prices, sorted, "Prices were not sorted low to high");
    }
}
