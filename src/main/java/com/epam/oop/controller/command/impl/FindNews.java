package com.epam.oop.controller.command.impl;

import com.epam.oop.bean.News;
import com.epam.oop.controller.command.Command;
import com.epam.oop.controller.command.exception.CommandExecutionException;
import com.epam.oop.service.exception.ServiceException;
import com.epam.oop.service.factory.ServiceFactory;

import java.util.List;

/**
 * Implements Command interface for search command.
 *
 * @author Uladzislau Seuruk.
 */
public class FindNews implements Command {
    /**
     * Name of command.
     */
    public static final String COMMAND_NAME = "FIND_NEWS";

    /**
     * @see Command#execute(String)
     */
    @Override
    public String execute(String params) throws CommandExecutionException {
        try {
            ServiceFactory factory = ServiceFactory.getInstance();
            List<News> newsList = factory.getCatalogService().getNews(params);
            StringBuilder builder = new StringBuilder();
            for (News news : newsList) {
                builder.append(getNewsInfo(news));
            }
            return "Found news:" + builder.toString();
        } catch (ServiceException se) {
            throw new CommandExecutionException(se.getMessage(), se);
        }
    }

    private String getNewsInfo(News news) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n")
                .append("\"")
                .append(news.getTitle())
                .append("\" Category: \"")
                .append(news.getCategory())
                .append("\" Publication Date: ")
                .append(news.getPublicationDate());
        return  builder.toString();
    }
}