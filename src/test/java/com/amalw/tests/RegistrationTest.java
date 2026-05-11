package com.amalw.tests;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.amalw.base.BaseTest;
import com.amalw.driver.DriverFactory;
import com.amalw.pages.RegisterPage;
import com.amalw.reports.ExtentLogger;
import com.amalw.reports.ExtentTestManager;
import com.amalw.utils.ScreenshotManager;
import com.aventstack.extentreports.MediaEntityBuilder;

/*RegistrationTest validates user registration functionality using multiple data sets.*/

public class RegistrationTest extends BaseTest {

	// DataProvider supplying multiple registration test data
	@DataProvider(name = "registrationData", parallel = true)
	public Object[][] getRegistrationData() {
		return new Object[][] { { "John", "Doe", "male", "ABC", "Pass123!", "Pass123!" },
				{ "Emma", "Stone", "female", "CDE", "Pass123!", "Pass123!" },
				{ "Tim", "James", "Male", "GHTG", "Pas1s123!", "Pass123!" },
				{ "Liam", "Brown", "male", "EFG", "Pass123!", "Pass123!" } };
	}

	// Test method for user registration using different input data sets
	@Test(dataProvider = "registrationData")
	public void testRegistration(String firstName, String lastName, String gender, String company, String password,
			String conPassword) {

		ExtentLogger.info(String.format("Executing registration test for user: %s %s", firstName, lastName));

		// Generate unique email to avoid duplication issues
		String email2 = UUID.randomUUID() + "@example.com";
		ExtentTestManager.getTest().info("Generated email: " + email2);

		// Create page object instance
		RegisterPage registerPage = new RegisterPage();

		ExtentLogger.action("Open registration page");
		registerPage.open();
		// attachScreenshot("Page Opened");

		ExtentLogger.action("Select gender: " + gender);
		registerPage.selectGender(gender);
		// attachScreenshot("Gender Selected");

		ExtentLogger.action("Fill registration form");
		registerPage.fillForm(firstName, lastName, email2, company, password, conPassword);
		// attachScreenshot("Form Filled");

		ExtentLogger.action("Submit registration form");
		registerPage.submit();
		// attachScreenshot("Form Submitted");

		ExtentLogger.validation("Verify registration success");
		Assert.assertTrue(registerPage.isRegistrationSuccessful(),
				"Expected registration success message, but it was not displayed");
		// attachScreenshot("Registration Success");

		String message = registerPage.getConfirmationMessage();
		ExtentTestManager.getTest().info("Confirmation message: " + message);

		Assert.assertTrue(message.contains("registration completed"));

	}

	/*
	 * // Attach screenshots to each step. Debugging purposes.
	 * 
	 * private void attachScreenshot(String stepName) { String path =
	 * ScreenshotManager.screenCapture( DriverFactory.getDriver(),
	 * this.getClass().getSimpleName(), stepName );
	 * 
	 * ExtentTestManager.getTest().info(stepName,
	 * MediaEntityBuilder.createScreenCaptureFromPath(path).build()); }
	 */

}