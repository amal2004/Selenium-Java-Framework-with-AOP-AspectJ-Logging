package com.amalw.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.amalw.driver.DriverFactory;
import com.amalw.reports.ExtentManager;
import com.amalw.reports.ExtentTestManager;
import com.amalw.utils.ScreenshotManager;
import com.aventstack.extentreports.ExtentTest;

/*
 * TestNG listener that integrates ExtentReports with test lifecycle.
 * Responsible for:
 * - Creating test entries in report
 * - Logging test status (pass/fail/skip)
 * - Capturing screenshots on failure
 * - Flushing report at execution end
 */
public class ExtentTestListener implements ITestListener {

    /*
     * Executes once before any test starts
     * Initializes ExtentReports singleton
     */
    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getInstance();
    }

    /*
     * Triggered when each test method starts
     * Creates a new ExtentTest entry per test method
     */
    @Override
    public void onTestStart(ITestResult result) {

        // Create test name using method name + first parameter (if any)
        ExtentTest test = ExtentManager.getInstance()
                .createTest(
                        result.getMethod().getMethodName()
                                + " - "
                                + result.getParameters()[0]
                );

        // Bind ExtentTest to current thread
        ExtentTestManager.setTest(test);
    }

    /*
     * Marks test as passed in Extent report
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().pass("Test passed");
    }

    /*
     * Handles test failure scenario
     * Logs exception + captures screenshot if driver is available
     */
    @Override
    public void onTestFailure(ITestResult result) {

        // Get active WebDriver instance
        WebDriver driver = DriverFactory.getDriver();

        // Extract class and method info for screenshot naming
        String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();

        // Get current ExtentTest instance
        ExtentTest test = ExtentTestManager.getTest();

        // Log exception in report
        test.fail(result.getThrowable());

        // Capture screenshot only if driver is active
        if (driver != null) {

            String path = ScreenshotManager.screenCapture(
                    driver,
                    className,
                    methodName
            );

            // Attach screenshot to report
            test.addScreenCaptureFromPath(path);
        }
    }

    /*
     * Marks test as skipped in report
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().skip("Test skipped");
    }

    /*
     * Executes after all tests in suite finish
     * Flushes report and clears thread-local storage
     */
    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
        ExtentTestManager.unload();
    }
}