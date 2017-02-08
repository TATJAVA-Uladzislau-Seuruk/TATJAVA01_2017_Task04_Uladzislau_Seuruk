package com.epam.oop.dao;

import com.epam.oop.bean.News;
import com.epam.oop.dao.exception.DaoException;

import java.util.List;

/**
 * Provides public interface for DAO layer.
 *
 * @author Uladzislau Seuruk.
 */
public interface NewsDao {
    /**
     * Adds new news.
     *
     * @param news <tt>News</tt> to add.
     * @throws DaoException if there were some troubles occurred during execution.
     */
    void addNews(News news) throws DaoException;

    /**
     * Returns <tt>List</tt> with <tt>News</tt> that matches received tags.
     *
     * @param tags keywords for searching.
     * @return <tt>List</tt> with <tt>News</tt> that matches received tags.
     * @throws DaoException if there were some troubles occurred during execution.
     */
    List<News> getNews(String... tags) throws DaoException;
}