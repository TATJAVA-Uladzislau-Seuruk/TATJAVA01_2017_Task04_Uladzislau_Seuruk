package com.epam.oop.service.util.exception;

/**
 * Can be thrown if there were some troubles occurred while news build.
 *
 * @author Uladzislau Seuruk.
 */
public class NewsBuildException extends Exception {
    public NewsBuildException() {}

    public NewsBuildException(Exception e) {
        super(e);
    }

    public NewsBuildException(String message) {
        super(message);
    }

    public NewsBuildException(String message, Exception e) {
        super(message, e);
    }
}