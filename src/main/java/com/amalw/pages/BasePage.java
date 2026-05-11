package com.amalw.pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import com.amalw.context.TestExecutionContext;
import com.amalw.driver.DriverFactory;

public abstract class BasePage {

	private static final int DEFAULT_TIMEOUT = 15;

	protected WebDriver driver() {
		return DriverFactory.getDriver();
	}

	protected WebDriverWait getWait() {
		return new WebDriverWait(driver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
	}

	/*
	 * Click element
	 */
	protected void click(By locator) {
		storeLocator(locator);
		getWait().until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	/*
	 * Type text
	 */
	protected void type(By locator, String text) {
		storeLocator(locator);
		WebElement element = getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
		element.clear();
		element.sendKeys(text);
	}

	/*
	 * Get text
	 */
	protected String getText(By locator) {
		storeLocator(locator);
		return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
	}

	/*
	 * Navigate to URL
	 */
	protected void navigateTo(String url) {
		driver().get(url);
	}

	/*
	 * Element displayed validation
	 */
	protected boolean isElementDisplayed(By locator, int timeoutSeconds) {

		storeLocator(locator);
		try {
			new WebDriverWait(driver(), Duration.ofSeconds(timeoutSeconds))
					.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;

		} catch (TimeoutException e) {
			return false;
		}
	}

	/*
	 * Wait for element visible
	 */
	protected void waitForElementVisible(By locator) {
		storeLocator(locator);
		getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/*
	 * Centralized locator tracking
	 */
	private void storeLocator(By locator) {
		if (locator != null) {
			TestExecutionContext.setLocator(locator);
		}
	}
}