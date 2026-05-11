package com.amalw.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/*
 * Singleton manager for ExtentReports instance.
 * Ensures only one report instance is created per test execution.
 */
public final class ExtentManager {

    // Single shared report instance
    private static ExtentReports extent;

    private ExtentManager() {
        // Prevent instantiation
    }

    /*
     * Initializes ExtentReports if not already created
     * Uses Spark Reporter for HTML report generation
     */
    public static ExtentReports getInstance() {

        if (extent == null) {

            // HTML report output location
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/index.html");

            // Report metadata configuration
            spark.config().setReportName("Automation Test Results");
            spark.config().setDocumentTitle("Test Execution Report");

            // Attach reporter to main Extent object
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // System-level information displayed in report dashboard
            extent.setSystemInfo("Framework", "Selenium Parallel");
            extent.setSystemInfo("Author", "Amal");
        }

        return extent;
    }
}