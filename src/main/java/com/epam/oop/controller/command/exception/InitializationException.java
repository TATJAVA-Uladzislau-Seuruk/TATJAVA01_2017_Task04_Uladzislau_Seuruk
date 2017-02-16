package com.epam.oop.controller.command.exception;

/**
 * Can be thrown if there were some troubles occurred while initialization required program resources.
 *
 * @author Uladzislau Seuruk.
 */
public class InitializationException extends RuntimeException {
    public InitializationException() {}

    public InitializationException(Exception e) {
        super(e);
    }

    public InitializationException(String message) {
        super(message);
    }

    public InitializationException(String message, Exception e) {
        super(message, e);
    }
}
