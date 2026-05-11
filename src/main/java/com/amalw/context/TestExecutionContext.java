package com.amalw.context;

import org.openqa.selenium.By;

/*
 * Stores thread-safe runtime execution data
 * used across framework layers, AOP logging,
 * reporting, listeners, and failure analysis.
 */

public final class TestExecutionContext {

	private TestExecutionContext() {
	}

	// Current test step
	private static final ThreadLocal<String> STEP = new ThreadLocal<>();

	// Last interacted locator
	private static final ThreadLocal<By> LOCATOR = new ThreadLocal<>();

	// Runtime test data
	private static final ThreadLocal<String> TEST_DATA = new ThreadLocal<>();

	// Browser metadata
	private static final ThreadLocal<String> BROWSER_INFO = new ThreadLocal<>();

	/*
	 * STEP
	 */

	public static void setStep(String step) {
		STEP.set(step);
	}

	public static String getStep() {
		return STEP.get();
	}

	/*
	 * LOCATOR
	 */

	public static void setLocator(By locator) {
		LOCATOR.set(locator);
	}

	public static By getLocator() {
		return LOCATOR.get();
	}

	/*
	 * TEST DATA
	 */

	public static void setTestData(String data) {
		TEST_DATA.set(data);
	}

	public static String getTestData() {
		return TEST_DATA.get();
	}

	/*
	 * BROWSER INFO
	 */

	public static void setBrowserInfo(String browserInfo) {

		BROWSER_INFO.set(browserInfo);
	}

	public static String getBrowserInfo() {

		String info = BROWSER_INFO.get();

		return info == null ? "Browser info unavailable" : info;
	}

	/*
	 * CLEANUP
	 */

	public static void clear() {

		STEP.remove();

		LOCATOR.remove();

		TEST_DATA.remove();

		BROWSER_INFO.remove();
	}
}