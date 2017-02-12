package com.epam.oop.controller.command.util.exception;

/**
 * Can be thrown if there were some troubles occurred while parsing command parameters.
 *
 * @author Uladzislau Seuruk.
 */
public class ParameterParsingException extends Exception {
    public ParameterParsingException() {}

    public ParameterParsingException(Exception e) {
        super(e);
    }

    public ParameterParsingException(String message) {
        super(message);
    }

    public ParameterParsingException(String message, Exception e) {
        super(message, e);
    }
}