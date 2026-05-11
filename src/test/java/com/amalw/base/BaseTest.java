package com.amalw.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.amalw.context.TestExecutionContext;
import com.amalw.driver.DriverFactory;
import com.amalw.reports.ExtentLogger;
import com.amalw.reports.ExtentManager;
import com.amalw.reports.ExtentTestManager;


/* BaseTest is the foundation for all test classes. All test classes extend the BaseTest */

public class BaseTest {

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(
            @Optional("chrome")
            String browser) {

        DriverFactory.initDriver(browser);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        DriverFactory.quitDriver();

        TestExecutionContext.clear();
    }
}
