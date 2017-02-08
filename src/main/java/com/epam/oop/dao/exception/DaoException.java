package com.epam.oop.dao.exception;

/**
 * Exception for DAO layer.
 *
 * @author Uladzislau Seuruk.
 */
public class DaoException extends Exception {
    public DaoException() {}

    public DaoException(Exception e) {
        super(e);
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Exception e) {
        super(message, e);
    }
}