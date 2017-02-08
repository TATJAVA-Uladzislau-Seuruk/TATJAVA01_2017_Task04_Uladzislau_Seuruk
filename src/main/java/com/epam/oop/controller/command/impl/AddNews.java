package com.epam.oop.controller.command.impl;

import com.epam.oop.controller.command.Command;
import com.epam.oop.controller.command.exception.CommandExecutionException;
import com.epam.oop.service.exception.ServiceException;
import com.epam.oop.service.factory.ServiceFactory;

/**
 * Implements Command interface for add new news command.
 *
 * @author Uladzislau Seuruk.
 */
public class AddNews implements Command {
    /**
     * Name of command.
     */
    public static final String COMMAND_NAME = "ADD_NEWS";

    /**
     * @see Command#execute(String)
     */
    @Override
    public String execute(String params) throws CommandExecutionException {
        try {
            ServiceFactory factory = ServiceFactory.getInstance();
            factory.getCatalogService().addNews(params);
        } catch (ServiceException e) {
            throw new CommandExecutionException(e.getMessage(), e);
        }
        return "News was successfully added.";
    }
}