package com.epam.oop.dao.util.reader.exception;

/**
 * Can be thrown if there were some troubles occurred while reading data from some source.
 *
 * @author Uladzislau Seuruk.
 */
public class ReadingException extends Exception {
    public ReadingException() {}

    public ReadingException(Exception e) {
        super(e);
    }

    public ReadingException(String message) {
        super(message);
    }

    public ReadingException(String message, Exception e) {
        super(message, e);
    }
}