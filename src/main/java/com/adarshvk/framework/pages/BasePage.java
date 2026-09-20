package com.adarshvk.framework.pages;

import com.adarshvk.framework.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Every page object extends this and calls waitUntilLoaded(...) at the end of
 * its constructor, passing a locator for one element that only exists once
 * that page has actually rendered.
 *
 * This is what page-transition methods (loginAs(), goToCart(), etc.) rely on:
 * "new SomePage(driver)" right after a click is only handed back once
 * SomePage is verifiably ready - not the instant the click event fires.
 * Without this, callers race the app's navigation and get NoSuchElementException
 * or stale-page assertions depending on which one loses the race that run.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait.seconds")));
    }

    protected void waitUntilLoaded(By pageIdentifyingLocator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(pageIdentifyingLocator));
    }
}
