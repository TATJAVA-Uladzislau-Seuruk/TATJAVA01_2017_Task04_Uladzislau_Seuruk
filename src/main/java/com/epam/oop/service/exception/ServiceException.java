package com.epam.oop.service.exception;

/**
 * Exception for Service layer.
 *
 * @author Uladzislau Seuruk.
 */
public class ServiceException extends Exception {
    public ServiceException() {}

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}