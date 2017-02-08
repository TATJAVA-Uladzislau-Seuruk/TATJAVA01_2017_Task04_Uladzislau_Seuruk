package com.epam.oop.dao.util.writer.exception;

/**
 * Can be thrown if there were some troubles occurred while writing data to some source.
 *
 * @author Uladzislau Seuruk.
 */
public class WritingException extends Exception {
    public WritingException() {}

    public WritingException(Exception e) {
        super(e);
    }

    public WritingException(String message) {
        super(message);
    }

    public WritingException(String message, Exception e) {
        super(message, e);
    }
}