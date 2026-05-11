package com.amalw.aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.amalw.context.TestExecutionContext;
import com.amalw.driver.DriverFactory;
import com.amalw.reports.FrameworkLogger;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

/*
 * AOP aspect that captures any test failure across framework execution.
 * Provides centralized debugging information like:
 * - step information
 * - browser details
 * - locator
 * - current URL
 * - test data
 * - screenshots
 * - failure diagnostics
 * - execution duration
 */

@Aspect
public class FailureLoggingAspect {

    // Intercepts any exception thrown in application layer methods
    @AfterThrowing(pointcut = "execution(* com.amalw..*(..))", throwing = "ex")
    public void logFailure(Throwable ex) {

        // Get current TestNG test result from Reporter context
        ITestResult result = Reporter.getCurrentTestResult();

        // If no test context exists, exit safely
        if (result == null) return;

        // Capture screenshot at failure point
        String screenshot = ScreenshotUtils.capture(result.getName());

        // Retrieve last used locator from execution context
        String locator = String.valueOf(TestExecutionContext.getLocator());

        // Build meaningful failure message
        String actualFailure = resolveActualFailure(ex, locator);

        // Resolve expected outcome based on step
        String expectedResult = resolveExpectedResult();

        // Centralized failure logging
        FrameworkLogger.logFailure(
                DriverFactory.getBrowserName(),                  // browser info
                result.getName(),                                // test name
                TestExecutionContext.getStep(),                  // step name
                result.getEndMillis() - result.getStartMillis(), // execution time
                expectedResult,                                  // expected result
                actualFailure,                                   // actual failure
                locator,                                         // locator used
                DriverFactory.getDriver().getCurrentUrl(),       // current URL
                TestExecutionContext.getTestData(),              // test data
                ex.getClass().getSimpleName(),                   // exception type
                screenshot                                       // screenshot path
        );
    }

    /*
     * Converts raw exceptions into human-readable failure messages
     */
    private String resolveActualFailure(Throwable ex, String locator) {

        if (ex instanceof TimeoutException) {
            return String.format("Element located by %s was not visible within timeout", locator);
        }

        if (ex instanceof NoSuchElementException) {
            return String.format("Element located by %s was not found in DOM", locator);
        }

        if (ex instanceof StaleElementReferenceException) {
            return String.format("Element located by %s became stale during interaction", locator);
        }

        if (ex instanceof AssertionError) {
            return ex.getMessage();
        }

        // Default fallback message
        return ex.getMessage() == null ? "Unexpected framework failure" : ex.getMessage();
    }

    /*
     * Maps execution step to expected business behavior
     */
    private String resolveExpectedResult() {

        String step = TestExecutionContext.getStep();

        if (step == null) {
            return "Expected result unavailable";
        }

        switch (step.toLowerCase()) {

            case "verify registration success":
                return "Registration success message should be visible";

            case "submit registration form":
                return "Registration form should submit successfully";

            default:
                return "Step should complete successfully";
        }
    }
}