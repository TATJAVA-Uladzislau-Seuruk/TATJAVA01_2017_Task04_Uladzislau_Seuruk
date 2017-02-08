package com.epam.oop.dao.util.parser.exception;

/**
 * Can be thrown if there were some troubles occurred while parsing single item.
 *
 * @author Uladzislau Seuruk.
 */
public class ItemParsingException extends Exception {
    public ItemParsingException() {}

    public ItemParsingException(Exception e) {
        super(e);
    }

    public ItemParsingException(String message) {
        super(message);
    }

    public ItemParsingException(String message, Exception e) {
        super(message, e);
    }
}