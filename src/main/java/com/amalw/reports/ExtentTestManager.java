package com.amalw.reports;

import com.aventstack.extentreports.ExtentTest;

/*
 * Thread-safe manager for ExtentTest instances.
 * Ensures parallel test execution isolation using ThreadLocal.
 */
public final class ExtentTestManager {

    // Each thread gets its own ExtentTest instance
    private static final ThreadLocal<ExtentTest> tlTest = new ThreadLocal<>();

    private ExtentTestManager() {
        // Prevent instantiation
    }

    // Assign ExtentTest to current thread
    public static void setTest(ExtentTest test) {
        tlTest.set(test);
    }

    // Retrieve current thread’s ExtentTest
    public static ExtentTest getTest() {
        return tlTest.get();
    }

    // Cleanup after test execution to prevent memory leaks
    public static void unload() {
        tlTest.remove();
    }
}