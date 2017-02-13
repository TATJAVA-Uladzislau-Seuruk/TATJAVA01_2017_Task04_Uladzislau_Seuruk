package com.epam.oop.controller;

import com.epam.oop.controller.command.Command;
import com.epam.oop.service.ServiceResourceManager;
import com.epam.oop.service.exception.ServiceException;
import com.epam.oop.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of Controller layer.
 *
 * @author Uladzislau Seuruk.
 */
public final class Controller {
    private static final Logger LOG = LogManager.getRootLogger();
    /**
     * Symbol that separates command form parameters.
     */
    private static final char DELIMITER = ' ';
    /**
     * Repository with known commands.
     */
    private final CommandProvider provider = new CommandProvider();
    /**
     * Instance of this class.
     */
    private static Controller instance = new Controller();

    /**
     * Returns instance of this class.
     */
    public static Controller getInstance() {
        return instance;
    }

    private Controller() {}

    /**
     * Executes received command.
     *
     * @param request <tt>String</tt> with command and its parameters.
     * @return <tt>String</tt> with response.
     */
    public String executeCommand(String request) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(request);
        }
        if (request == null) {
            return "String with response was not initialized.";
        }
        request = request.trim();
        String[] parts = request.split(String.valueOf(DELIMITER), 2);
        Command command = provider.getCommand(parts[0]);
        String params = "";
        if (parts.length > 1) {
            params = parts[1];
        }
        String response = command.execute(params);
        if (LOG.isDebugEnabled()) {
            LOG.debug(response);
        }
        return response;
    }

    /**
     * TODO
     */
    public void finish() {
        ServiceResourceManager manager = ServiceFactory.getInstance().getResourceManager();
        manager.freeResources();
    }

    /**
     * TODO:
     *
     * @return
     */
    public String start() {
        String response;
        try {
            ServiceResourceManager manager = ServiceFactory.getInstance().getResourceManager();
            manager.initResources();
            response = "Hello!";
        } catch (ServiceException e) {
            LOG.error("Resources initialization error.", e);
            response = "Resources initialization error.";
        }
        return response;
    }
}