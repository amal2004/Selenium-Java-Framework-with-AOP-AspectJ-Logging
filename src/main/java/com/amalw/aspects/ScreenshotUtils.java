package com.amalw.aspects;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.*;

import com.amalw.driver.DriverFactory;

/*
 * Utility responsible for capturing screenshots on failure
 */
public class ScreenshotUtils {

    public static String capture(String testName) {

        try {

            // Capture screenshot from active WebDriver session
            File src = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.FILE);

            // Generate unique filename per execution
            String fileName = testName + "_" + System.currentTimeMillis() + ".png";

            // Store inside /screenshots directory
            Path destination = Paths.get("screenshots", fileName);

            // Ensure directory exists
            Files.createDirectories(destination.getParent());

            // Copy screenshot to destination
            Files.copy(src.toPath(), destination);

            return destination.toString();

        } catch (Exception e) {

            // Fail-safe return if screenshot fails
            return "Unable to capture screenshot";
        }
    }
}