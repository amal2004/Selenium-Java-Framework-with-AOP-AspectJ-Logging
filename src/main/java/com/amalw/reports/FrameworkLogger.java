package com.amalw.reports;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Console-based fallback logger for failures.
 * Used for deep debugging when ExtentReports is not enough or not initialized.
 */
public class FrameworkLogger {

    /*
     * Logs full failure context in structured format
     * Used by AOP failure aspect for debugging production-level issues
     */
    public static void logFailure(
            String browser,
            String testName,
            String step,
            long duration,
            String expected,
            String actual,
            String locator,
            String url,
            String data,
            String exception,
            String screenshot) {

        System.out.println("\n=================================================\n");

        System.out.println("[FAILURE DETECTED]\n");

        // Timestamp of failure
        System.out.println("Timestamp:");
        System.out.println(time());

        // Thread info helps debug parallel execution issues
        System.out.println("\nThread:");
        System.out.println(Thread.currentThread().getName());

        System.out.println("\nBrowser:");
        System.out.println(browser);

        System.out.println("\nTest:");
        System.out.println(testName);

        System.out.println("\nStep:");
        System.out.println(step);

        System.out.println("\nDuration:");
        System.out.println(duration + " ms");

        System.out.println("\nExpected:");
        System.out.println(expected);

        System.out.println("\nActual:");
        System.out.println(actual);

        System.out.println("\nLocator:");
        System.out.println(locator);

        System.out.println("\nCurrent URL:");
        System.out.println(url);

        System.out.println("\nTest Data:");
        System.out.println(data);

        System.out.println("\nException:");
        System.out.println(exception);

        System.out.println("\nScreenshot:");
        System.out.println(screenshot);

        System.out.println("\n=================================================");
    }

    /*
     * Generates formatted timestamp for failure logs
     */
    private static String time() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}