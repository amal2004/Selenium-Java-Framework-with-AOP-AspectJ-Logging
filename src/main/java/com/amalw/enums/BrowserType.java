package com.amalw.enums;

import com.amalw.exceptions.FrameworkException;

/* BrowserType defines the supported browser options */

public enum BrowserType {

	// Supported browser types
	CHROME, FIREFOX, EDGE;

	// Converts string input into corresponding BrowserType enum
	public static BrowserType from(String value) {

		// Validate input value
		if (value == null || value.trim().isEmpty()) {
			throw new FrameworkException("Browser value is missing in config");
		}

		try {
			// Convert string to uppercase and map to enum
			return BrowserType.valueOf(value.trim().toUpperCase());

		} catch (IllegalArgumentException e) {
			// Handle invalid browser values
			throw new FrameworkException("Invalid browser: " + value);
		}
	}
}
