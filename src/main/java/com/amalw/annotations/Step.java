package com.amalw.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Keep annotation available at runtime for AOP interception
@Retention(RetentionPolicy.RUNTIME)

//Restrict usage to methods only
@Target(ElementType.METHOD)
public @interface Step {

 // Descriptive step name used in reporting and logging
 String value();
}