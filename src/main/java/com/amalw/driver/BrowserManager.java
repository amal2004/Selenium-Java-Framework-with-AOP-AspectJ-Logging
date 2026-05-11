package com.amalw.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.amalw.enums.BrowserType;
import com.amalw.exceptions.FrameworkException;
import com.amalw.reports.ExtentLogger;

import io.github.bonigarcia.wdm.WebDriverManager;

public final class BrowserManager {

	private BrowserManager() {
	}

	// This will avoid multiple WebDriverManager calls within the switch block
	static {
		WebDriverManager.chromedriver().setup();
		WebDriverManager.firefoxdriver().setup();
		WebDriverManager.edgedriver().setup();
	}

	// Create driver based on browser type using values from enums
	public static WebDriver createDriver(BrowserType browserType, boolean headless) {

		ExtentLogger.driver(
				String.format("Initializing browser session | browser=%s | headless=%s", browserType, headless));

		switch (browserType) {
		case CHROME:
			return new ChromeDriver(chromeOptions(headless));

		case FIREFOX:
			return new FirefoxDriver(firefoxOptions(headless));

		case EDGE:
			return new EdgeDriver(edgeOptions(headless));

		default:
			ExtentLogger.error("Unsupported browser type: " + browserType);
			throw new FrameworkException("Unsupported browser: " + browserType);
		}
	}

	// Configure Chrome options
	private static ChromeOptions chromeOptions(boolean headless) {
		ChromeOptions options = new ChromeOptions();
		if (headless) {
			// Arguments specific to headless testing
			options.addArguments("--headless=new", "--window-size=1920,1080", "--disable-gpu", "--no-sandbox",
					"--disable-dev-shm-usage");
		}
		options.addArguments("--disable-notifications", "--start-maximized");
		return options;
	}

	// Configure Firefox options
	private static FirefoxOptions firefoxOptions(boolean headless) {
		FirefoxOptions options = new FirefoxOptions();

		if (headless) {
			// Arguments specific to headless testing
			options.addArguments("-headless", "--width=1920", "--height=1080");
		}

		return options;
	}

	// Configure Edge options
	private static EdgeOptions edgeOptions(boolean headless) {
		EdgeOptions options = new EdgeOptions();
		if (headless) {
			options.addArguments("--headless=new", "--window-size=1920,1080", "--disable-gpu", "--no-sandbox",
					"--disable-dev-shm-usage", "--disable-notifications");
		}
		return options;
	}
}
