package com.epam.oop.service.impl;

import com.epam.oop.bean.Category;
import com.epam.oop.bean.News;
import com.epam.oop.dao.exception.DaoException;
import com.epam.oop.dao.factory.DaoFactory;
import com.epam.oop.service.CatalogService;
import com.epam.oop.service.exception.ServiceException;
import com.epam.oop.service.util.NewsBuilder;
import com.epam.oop.service.util.exception.NewsBuildException;

import java.util.List;

/**
 * Implementation of Service layer.
 *
 * @author Uladzislau Seuruk.
 */
public class CatalogServiceImpl implements CatalogService {
    /**
     * Pattern for date validation.
     */
    private static final String DATE_PATTERN ="^\\d{4}-((0?\\d)|(1[012]))-((0?\\d)|([12]\\d)|(3[01]))$";

    @Override
    public void addNews(Category category, String title) throws ServiceException {
        try {
            NewsBuilder builder = new NewsBuilder();
            News news = builder.build(category, title);
            DaoFactory factory = DaoFactory.getInstance();
            factory.getNewsDao().addNews(news);
        } catch (DaoException | NewsBuildException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> getNews(String[] tags) throws ServiceException {
        checkParams(tags);
        try {
            DaoFactory factory = DaoFactory.getInstance();
            return factory.getNewsDao().getNews(tags);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> getNewsByCategory(Category category) throws ServiceException {
        if (category == null) {
            throw new ServiceException("Category was not initialized.");
        }
        try {
            DaoFactory factory = DaoFactory.getInstance();
            return factory.getNewsDao().getNewsByCategory(category);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> getNewsByTitle(String[] tags) throws ServiceException {
        checkParams(tags);
        try {
            DaoFactory factory = DaoFactory.getInstance();
            return factory.getNewsDao().getNewsByTitle(tags);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> getNewsSinceDate(String date) throws ServiceException {
        if (!isDateValid(date)) {
            throw new ServiceException("Received date has invalid format.");
        }
        try {
            DaoFactory factory = DaoFactory.getInstance();
            return factory.getNewsDao().getNewsSinceDate(date);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void checkParams(String[] params) throws ServiceException {
        if (params == null) {
            throw new ServiceException("Array with parameters was not initialized.");
        }
        for (String tag : params) {
            if (tag == null) {
                throw new ServiceException("String with parameter was not initialized.");
            }
            if (tag.isEmpty()) {
                throw new ServiceException("String with parameter is empty.");
            }
        }
    }

    private boolean isDateValid(String date) {
        return date != null && date.matches(DATE_PATTERN);
    }
}