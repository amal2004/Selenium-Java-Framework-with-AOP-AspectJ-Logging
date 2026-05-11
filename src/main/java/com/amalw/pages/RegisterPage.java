package com.amalw.pages;

import org.openqa.selenium.By;

import com.amalw.annotations.Step;
import com.amalw.config.ConfigManager;
import com.amalw.context.TestExecutionContext;
import com.amalw.reports.ExtentLogger;

/* Provides actions to interact with registration form elements */

public class RegisterPage extends BasePage {

	// Locators
	private By genderMale = By.id("gender-male");
	private By genderFemale = By.id("gender-female");
	private By firstName = By.id("FirstName");
	private By lastName = By.id("LastName");
	private By email = By.id("Email");
	private By company = By.id("Company");
	private By password = By.id("Password");
	private By confirmPassword = By.id("ConfirmPassword");
	private By registerButton = By.id("register-button");
	private By successMsg = By.cssSelector("div.result");
	private By emailError = By.id("Email-error");

	@Step("Open Registration Page")
	public void open() {
		navigateTo(ConfigManager.get("base.url") + "/register");
	}

	public void selectGender(String gender) {
		if (gender.equalsIgnoreCase("male"))
			click(genderMale);
		else if (gender.equalsIgnoreCase("female"))
			click(genderFemale);
	}

	@Step("Fill Registration Form")
	public RegisterPage fillForm(String fName, String lName, String emailAddr, String comp, String pwd, String conPwd) {

		// Store runtime test data for AOP logging
		TestExecutionContext.setTestData(String.format("firstName=%s, lastName=%s, email=%s", fName, lName, emailAddr));

		type(firstName, fName);
		type(lastName, lName);
		type(email, emailAddr);
		type(company, comp);
		type(password, pwd);
		type(confirmPassword, conPwd);

		return this;
	}

	@Step("Submit Registration Form")
	public RegisterPage submit() {
		click(registerButton);
		return this;
	}

	@Step("Verify Registration Success")

	public boolean isRegistrationSuccessful() {
		boolean status = isElementDisplayed(successMsg, 5);

		if (status) {
			ExtentLogger.validation("Registration completed successfully");
		}

		return status;
	}

	// Check if email validation error is displayed
	public boolean isEmailErrorDisplayed() {
		return isElementDisplayed(emailError, 5);
	}

	// Get success confirmation message text
	public String getConfirmationMessage() {
		return getText(successMsg);
	}
}