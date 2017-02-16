package com.epam.oop.service;

import com.epam.oop.bean.Category;
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
     * @param category <tt>News</tt> <tt>Category</tt>.
     * @param title <tt>String</tt> with <tt>News</tt> title.
     * @throws ServiceException if there were some troubles occurred during execution.
     */
    void addNews(Category category, String title) throws ServiceException;

    /**
     * Returns <tt>List</tt> with <tt>News</tt> that matches received tags.
     *
     * @param tags array with keywords for search.
     * @return <tt>List</tt> with <tt>News</tt> that matches received tags.
     * @throws ServiceException if there were some troubles occurred during execution.
     */
    List<News> getNews(String[] tags) throws ServiceException;

    /**
     * Returns <tt>List</tt> with <tt>News</tt> that matches received category.
     *
     * @param category <tt>Category</tt> to search.
     * @return <tt>List</tt> with <tt>News</tt> that matches received category.
     * @throws ServiceException if there were some troubles occurred during execution.
     */
    List<News> getNewsByCategory(Category category) throws ServiceException;

    /**
     * Returns <tt>List</tt> with <tt>News</tt> that was added after received date.
     *
     * @param date <tt>String</tt> with date for search.
     * @return <tt>List</tt> with <tt>News</tt> that matches received tags.
     * @throws ServiceException if there were some troubles occurred during execution.
     */
    List<News> getNewsSinceDate(String date) throws ServiceException;

    /**
     * Returns <tt>List</tt> with <tt>News</tt> which title contains received tags.
     *
     * @param tags array with keywords for search.
     * @return <tt>List</tt> with <tt>News</tt> which title contains received tags.
     * @throws ServiceException if there were some troubles occurred during execution.
     */
    List<News> getNewsByTitle(String[] tags) throws ServiceException;
}