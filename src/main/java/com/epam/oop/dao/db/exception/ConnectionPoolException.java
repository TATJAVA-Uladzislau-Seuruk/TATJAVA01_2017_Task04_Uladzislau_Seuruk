package com.epam.oop.dao.db.exception;

/**
 * Can be thrown if there were some troubles at connection pool.
 *
 * @author Uladzislau Seuruk.
 */
public class ConnectionPoolException extends Exception {
    public ConnectionPoolException() {}

    public ConnectionPoolException(Exception e) {
        super(e);
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
    }
}