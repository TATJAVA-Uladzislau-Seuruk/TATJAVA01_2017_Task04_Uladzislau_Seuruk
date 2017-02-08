package com.epam.oop.service;

import com.epam.oop.bean.News;
import com.epam.oop.service.exception.ServiceException;

import java.util.List;

/**
 * Provides public interface for Service layer.
 *
 * @author Uladzislau Seuruk.
 */
public interface CatalogService {
    /**
     * Adds new news with received parameters.
     *
     * @param params <tt>String</tt> with <tt>News</tt> parameters.
     * @throws ServiceException if there were some troubles occurred during execution.
     */
    void addNews(String params) throws ServiceException;

    /**
     * Returns <tt>List</tt> with <tt>News</tt> that matches received tags.
     *
     * @param tags <tt>String</tt> with keywords for search.
     * @return <tt>List</tt> with <tt>News</tt> that matches received tags.
     * @throws ServiceException if there were some troubles occurred during execution.
     */
    List<News> getNews(String tags) throws ServiceException;
}