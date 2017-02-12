package com.epam.oop.controller.command.impl;

import com.epam.oop.bean.News;
import com.epam.oop.controller.command.Command;
import com.epam.oop.controller.command.util.ParameterParser;
import com.epam.oop.controller.command.util.SearchReportGenerator;
import com.epam.oop.service.exception.ServiceException;
import com.epam.oop.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Implements Command interface for search by date command.
 *
 * @author Uladzislau Seuruk.
 */
public class FindNewsByDate implements Command {
    private static final Logger LOG = LogManager.getRootLogger();
    /**
     * Name of command.
     */
    public static final String COMMAND_NAME = "FIND_NEWS_BY_DATE";

    @Override
    public String execute(String params) {
        if (params == null) {
            return "String with params was not initialized.";
        }
        if (params.isEmpty()) {
            return "Search parameters are missing.";
        }
        String response;
        try {
            String paramsDelim = String.valueOf(ParameterParser.PARAMETER_DELIMITER);
            String[] paramsArray = params.split(paramsDelim);
            ServiceFactory factory = ServiceFactory.getInstance();
            List<News> newsList = factory.getCatalogService().getNewsByDate(paramsArray);
            response = SearchReportGenerator.generateReport(newsList);
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            response = "Error during news search.";
        }
        return response;
    }
}