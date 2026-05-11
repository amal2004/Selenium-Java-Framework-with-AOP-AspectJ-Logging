package com.amalw.reports;

import com.aventstack.extentreports.ExtentTest;

/*
 * Central logging utility for ExtentReports.
 * Provides structured logging levels for better test reporting consistency.
 */
public final class ExtentLogger {

    private ExtentLogger() {
        // Prevent instantiation (utility class)
    }

    // Fetch current thread’s ExtentTest instance
    private static ExtentTest get() {
        return ExtentTestManager.getTest();
    }

    // Standard log formatting: [LEVEL] message
    private static String format(String level, String message) {
        return String.format("[%s] %s", level, message);
    }

    /*
     * General information logs
     */
    public static void info(String message) {
        log("INFO", message);
    }

    public static void action(String message) {
        log("ACTION", message);
    }

    public static void validation(String message) {
        log("VALIDATION", message);
    }

    public static void config(String message) {
        log("CONFIG", message);
    }

    public static void driver(String message) {
        log("DRIVER", message);
    }

    /*
     * Test status logs
     */
    public static void pass(String message) {
        log("PASS", message);
    }

    public static void fail(String message) {
        log("FAIL", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }

    public static void warning(String message) {
        log("WARNING", message);
    }

    /*
     * Core logging engine:
     * - Writes to ExtentReports if available
     * - Falls back to console logging otherwise
     */
    private static void log(String level, String message) {

        String formatted = format(level, message);

        ExtentTest test = get();

        if (test != null) {

            // Route logs based on severity level
            switch (level) {
                case "PASS":
                    test.pass(formatted);
                    break;

                case "FAIL":
                    test.fail(formatted);
                    break;

                default:
                    test.info(formatted);
                    break;
            }

        } else {
            // Fallback if ExtentTest not initialized
            System.out.println(formatted);
        }
    }

    /*
     * Dedicated step logger for AOP / business flow tracking
     */
    public static void step(String message) {

        ExtentTest test = ExtentTestManager.getTest();

        // Log step into Extent report
        if (test != null) {
            test.info("[STEP] " + message);
        }

        // Always print to console for debugging visibility
        System.out.println("[STEP] " + message);
    }
}