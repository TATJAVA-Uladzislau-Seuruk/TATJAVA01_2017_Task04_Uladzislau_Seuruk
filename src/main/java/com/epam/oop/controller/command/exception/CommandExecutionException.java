package com.epam.oop.controller.command.exception;

/**
 * Can be thrown if there were some troubles occurred while command execution.
 *
 * @author Uladzislau Seuruk.
 */
public class CommandExecutionException extends Exception {
    public CommandExecutionException() {}

    public CommandExecutionException(Exception e) {
        super(e);
    }

    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(String message, Exception e) {
        super(message, e);
    }
}