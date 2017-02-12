package com.epam.oop.controller.command.util.exception;

/**
 * Can be thrown if there were some troubles occurred while parsing category command parameter.
 *
 * @author Uladzislau Seuruk.
 */
public class CategoryParsingException extends ParameterParsingException {
    public CategoryParsingException() {}

    public CategoryParsingException(Exception e) {
        super(e);
    }

    public CategoryParsingException(String message) {
        super(message);
    }

    public CategoryParsingException(String message, Exception e) {
        super(message, e);
    }
}