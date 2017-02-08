package com.epam.oop.service.util.exception;

/**
 * Can be thrown if there were some troubles occurred while parsing news parameters.
 *
 * @author Uladzislau Seuruk.
 */
public class NewsParamsParsingException extends Exception {
    public NewsParamsParsingException() {}

    public NewsParamsParsingException(Exception e) {
        super(e);
    }

    public NewsParamsParsingException(String message) {
        super(message);
    }

    public NewsParamsParsingException(String message, Exception e) {
        super(message, e);
    }
}