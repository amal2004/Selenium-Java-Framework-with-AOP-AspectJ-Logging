package com.amalw.exceptions;

/* Custom runtime exception used handle framework errors */
public class FrameworkException extends RuntimeException {

	// Constructor to initialize exception
	public FrameworkException(String message) {
		super(message);
	}
}
