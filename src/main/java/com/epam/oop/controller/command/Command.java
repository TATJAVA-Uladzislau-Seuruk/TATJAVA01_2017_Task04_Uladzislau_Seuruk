package com.epam.oop.controller.command;

import com.epam.oop.controller.command.exception.CommandExecutionException;

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
     * @throws CommandExecutionException if there were some troubles occurred during command execution.
     */
    String execute(String params) throws CommandExecutionException;
}