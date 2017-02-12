package com.epam.oop.dao.db.exception;

/**
 * Can be thrown if there were some troubles occurred during working with database.
 *
 * @author Uladzislau Seuruk.
 */
public class DbManagerException extends Exception {
    public DbManagerException() {}

    public DbManagerException(Exception e) {
        super(e);
    }

    public DbManagerException(String message) {
        super(message);
    }

    public DbManagerException(String message, Exception e) {
        super(message, e);
    }
}