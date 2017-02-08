package com.epam.oop.controller.command.impl;

import com.epam.oop.controller.command.Command;

/**
 * Implements Command interface for unknown command case.
 *
 * @author Uladzislau Seuruk.
 */
public class WrongRequest implements Command {
    /**
     * Name of command.
     */
    public static final String COMMAND_NAME = "WRONG_REQUEST";

    /**
     * @see Command#execute(String)
     */
    @Override
    public String execute(String params) {
        return "Unknown command.";
    }
}