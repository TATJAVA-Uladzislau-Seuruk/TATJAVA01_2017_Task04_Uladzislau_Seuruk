package com.epam.oop.util.converter.exception;

/**
 * Can be thrown if there were some troubles occurred while conversion.
 *
 * @author Uladzislau Seuruk.
 */
public class ConversionException extends Exception {
    public ConversionException() {}

    public ConversionException(Exception e) {
        super(e);
    }

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(String message, Exception e) {
        super(message, e);
    }
}