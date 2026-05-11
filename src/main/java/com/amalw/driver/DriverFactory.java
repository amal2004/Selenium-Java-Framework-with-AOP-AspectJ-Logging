package com.amalw.driver;

import java.time.Duration;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.amalw.config.ConfigManager;
import com.amalw.context.TestExecutionContext;
import com.amalw.enums.BrowserType;
import com.amalw.exceptions.FrameworkException;
import com.amalw.reports.ExtentLogger;

/*
 * DriverFactory is responsible for:
 * 
 * 1. Thread-safe WebDriver management
 * 2. Browser initialization
 * 3. Driver lifecycle management
 * 4. Browser/session logging
 * 5. Framework-level driver configuration
 * 6. Capturing runtime browser metadata for reporting/AOP logging
 */

public final class DriverFactory {

	// Thread-safe WebDriver storage
	private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

	// Stores browser name for reporting/logging
	private static final ThreadLocal<String> TL_BROWSER = new ThreadLocal<>();

	// Prevent object creation
	private DriverFactory() {
	}

	/*
	 * Returns current thread WebDriver instance
	 */
	public static WebDriver getDriver() {

		WebDriver driver = TL_DRIVER.get();

		if (driver == null) {

			ExtentLogger.error("WebDriver instance is NULL for current thread");

			throw new FrameworkException("Driver not initialized for current thread");
		}

		return driver;
	}

	/*
	 * Returns current browser name
	 */
	public static String getBrowserName() {

		String browser = TL_BROWSER.get();

		if (browser == null || browser.isBlank()) {
			return "UNKNOWN";
		}

		return browser;
	}

	/*
	 * Initializes WebDriver session
	 */
	public static synchronized void initDriver(String browserName) {

		// Prevent duplicate driver initialization
		if (TL_DRIVER.get() != null) {

			ExtentLogger.driver("Existing WebDriver session detected for thread: " + Thread.currentThread().getName());

			return;
		}

		// Resolve browser from parameter or config
		String resolvedBrowser = resolveBrowser(browserName);

		BrowserType browserType = BrowserType.from(resolvedBrowser);

		boolean headless = ConfigManager.getBoolean("headless", false);

		ExtentLogger.driver(
				String.format("Initializing browser session | browser=%s | headless=%s", browserType, headless));

		long start = System.currentTimeMillis();

		try {

			// Create WebDriver instance
			WebDriver driver = BrowserManager.createDriver(browserType, headless);

			// Configure browser session
			configureDriver(driver, headless);

			// Store thread-safe references
			TL_DRIVER.set(driver);

			TL_BROWSER.set(browserType.name());

			// Store runtime metadata for AOP logs
			storeBrowserMetadata(driver);

			long duration = System.currentTimeMillis() - start;

			ExtentLogger.driver(String.format("WebDriver initialized successfully | browser=%s | duration=%d ms",
					browserType, duration));

		} catch (Exception e) {

			ExtentLogger.error("Failed to initialize WebDriver: " + e.getMessage());

			throw new FrameworkException("Driver initialization failed");
		}
	}

	/*
	 * Configure WebDriver session
	 */
	private static void configureDriver(WebDriver driver, boolean headless) {

		// Maximize browser only for headed execution
		if (!headless) {

			driver.manage().window().maximize();

			ExtentLogger.driver("Browser window maximized");
		}

		// Avoid implicit wait conflicts
		driver.manage().timeouts().implicitlyWait(Duration.ZERO);

		// Page load timeout
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigManager.getInt("pageLoadTimeout", 60)));

		// Script timeout
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(ConfigManager.getInt("scriptTimeout", 30)));

		ExtentLogger.driver("Driver session configured successfully");
	}

	/*
	 * Resolves browser from parameter/config
	 */
	private static String resolveBrowser(String browserName) {

		String resolvedBrowser = (browserName == null || browserName.isBlank())

				? ConfigManager.get("browser")

				: browserName;

		ExtentLogger.driver("Resolved browser: " + resolvedBrowser);

		return resolvedBrowser;
	}

	/*
	 * Stores runtime browser metadata for reporting/AOP failure logging
	 */
	private static void storeBrowserMetadata(WebDriver driver) {

		try {

			Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();

			String browserName = capabilities.getBrowserName();

			String browserVersion = capabilities.getBrowserVersion();

			String platform = String.valueOf(capabilities.getPlatformName());

			TestExecutionContext.setBrowserInfo(String.format("%s %s | %s", browserName, browserVersion, platform));

			ExtentLogger.driver(
					String.format("Browser metadata captured | %s %s | %s", browserName, browserVersion, platform));

		} catch (Exception e) {

			ExtentLogger.warning("Unable to capture browser metadata");
		}
	}

	/*
	 * Closes browser session
	 */
	public static synchronized void quitDriver() {

		WebDriver driver = TL_DRIVER.get();

		if (driver == null) {

			ExtentLogger.warning("No WebDriver session found to close");

			return;
		}

		try {

			ExtentLogger.driver("Closing WebDriver session");

			driver.quit();

			ExtentLogger.driver("WebDriver session closed successfully");

		} catch (Exception e) {

			ExtentLogger.error("Failed to close browser session: " + e.getMessage());

		} finally {

			// Cleanup ThreadLocal storage
			TL_DRIVER.remove();

			TL_BROWSER.remove();

			ExtentLogger.driver("ThreadLocal WebDriver resources released");
		}
	}
}