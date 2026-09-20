package com.adarshvk.tests;

import com.adarshvk.framework.base.BaseTest;
import com.adarshvk.framework.pages.CartPage;
import com.adarshvk.framework.pages.CheckoutPage;
import com.adarshvk.framework.pages.LoginPage;
import com.adarshvk.framework.pages.ProductsPage;
import com.adarshvk.framework.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * End-to-end checkout flow: login -> add to cart -> checkout -> order confirmation.
 * This is the main regression path for the app - if this breaks, nothing else matters.
 */
public class CheckoutTests extends BaseTest {

    private CartPage cartPage;

    @BeforeMethod
    public void addItemToCartAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.loginAs(
                ConfigReader.get("standard.username"),
                ConfigReader.get("valid.password"));

        productsPage.addFirstNItemsToCart(1);
        cartPage = productsPage.goToCart();
    }

    @Test(description = "Completing checkout with valid shipping info confirms the order")
    public void validCheckout_completesOrderSuccessfully() {
        CheckoutPage checkoutPage = cartPage.proceedToCheckout()
                .fillShippingInfo("Adarsh", "V K", "673001");

        Assert.assertFalse(checkoutPage.isErrorDisplayed(),
                "Valid shipping info should not raise a validation error");

        checkoutPage.finishOrder();
        Assert.assertTrue(checkoutPage.isOrderComplete(),
                "Order confirmation message was not shown after finishing checkout");
    }

    @Test(description = "Checkout should reject missing shipping info with a validation error")
    public void checkoutWithMissingInfo_showsValidationError() {
        CheckoutPage checkoutPage = cartPage.proceedToCheckout()
                .fillShippingInfo("", "", "");

        Assert.assertTrue(checkoutPage.isErrorDisplayed(),
                "Expected a validation error when shipping info is missing");
    }
}
