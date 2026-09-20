package com.adarshvk.tests;

import com.adarshvk.framework.base.BaseTest;
import com.adarshvk.framework.pages.LoginPage;
import com.adarshvk.framework.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Functional and negative test coverage for the login flow.
 */
public class LoginTests extends BaseTest {

    @Test(description = "Valid credentials should land the user on the Products page")
    public void validLogin_navigatesToProductsPage() {
        LoginPage loginPage = new LoginPage(driver);
        var productsPage = loginPage.loginAs(
                ConfigReader.get("standard.username"),
                ConfigReader.get("valid.password"));

        Assert.assertTrue(productsPage.isLoaded(), "Products page did not load after valid login");
    }

    @Test(description = "Locked out user should be blocked with an error message")
    public void lockedOutUser_showsErrorMessage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(
                ConfigReader.get("locked.out.username"),
                ConfigReader.get("valid.password"));

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected an error message for a locked out user");
        Assert.assertTrue(
                loginPage.getErrorText().toLowerCase().contains("locked out"),
                "Error message did not mention the account being locked out");
    }

    @Test(description = "Empty credentials should be rejected with an error message")
    public void emptyCredentials_showsErrorMessage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("", "");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected an error message for empty credentials");
    }
}
