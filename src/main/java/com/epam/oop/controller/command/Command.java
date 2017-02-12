package com.epam.oop.controller.command;

/**
 * Implements Command design pattern.
 *
 * @author Uladzislau Seuruk.
 */
public interface Command {
    /**
     * Executes command.
     *
     * @param params <tt>String</tt> with command parameters.
     * @return <tt>String</tt> with response.
     */
    String execute(String params);
}