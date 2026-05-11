package com.amalw.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import com.amalw.annotations.Step;
import com.amalw.context.TestExecutionContext;
import com.amalw.reports.ExtentLogger;

/*
 * AOP aspect that wraps each @Step annotated method
 * for execution timing + reporting integration
 */
@Aspect
public class StepLoggingAspect {

    // Intercepts all methods annotated with @Step
    @Around("@annotation(step)")
    public Object logStep(ProceedingJoinPoint joinPoint, Step step) throws Throwable {

        // Start execution timer
        long start = System.currentTimeMillis();

        // Store step name in thread-safe context
        TestExecutionContext.setStep(step.value());

        // Log step start in Extent report
        ExtentLogger.step("STARTED: " + step.value());

        try {

            // Execute actual business method
            Object result = joinPoint.proceed();

            // Calculate execution time
            long duration = System.currentTimeMillis() - start;

            // Log success with duration
            ExtentLogger.pass(step.value() + " completed in " + duration + " ms");

            return result;

        } catch (Throwable ex) {

            // Log failure at step level
            ExtentLogger.fail(step.value() + " failed: " + ex.getMessage());

            // Re-throw exception for higher-level handlers
            throw ex;
        }
    }
}