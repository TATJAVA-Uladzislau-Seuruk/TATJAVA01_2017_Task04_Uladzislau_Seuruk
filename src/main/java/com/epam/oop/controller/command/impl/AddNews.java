package com.epam.oop.controller.command.impl;

import com.epam.oop.bean.Category;
import com.epam.oop.bean.Tag;
import com.epam.oop.controller.command.Command;
import com.epam.oop.controller.command.util.ParameterParser;
import com.epam.oop.controller.command.util.exception.CategoryParsingException;
import com.epam.oop.controller.command.util.exception.ParameterParsingException;
import com.epam.oop.service.exception.ServiceException;
import com.epam.oop.service.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

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
    private static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String params) {
        String response;
        try {
            Map<String, String> paramsMap = ParameterParser.parse(params);
            String categoryParam = paramsMap.get(Tag.CATEGORY.toString());
            if (categoryParam == null) {
                return "Category is missing.";
            }
            Category category = ParameterParser.parseCategory(categoryParam);
            String title = paramsMap.get(Tag.TITLE.toString());
            if (title == null) {
                return "Title is missing.";
            }
            if (title.isEmpty()) {
                return "Title is empty.";
            }
            ServiceFactory factory = ServiceFactory.getInstance();
            factory.getCatalogService().addNews(category, title);
            response = "News was successfully added.";
        } catch (CategoryParsingException e) {
            LOG.error(e.getMessage(), e);
            response = "Unknown category.";
        } catch (ParameterParsingException e) {
            LOG.error(e.getMessage(), e);
            response = "Illegal parameters marking.";
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            response = "News addition was failed.";
        }
        return response;
    }
}