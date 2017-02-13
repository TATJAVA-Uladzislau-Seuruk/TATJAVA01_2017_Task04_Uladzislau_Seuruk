package com.epam.oop.dao.impl;

import com.epam.oop.bean.Category;
import com.epam.oop.bean.News;
import com.epam.oop.dao.NewsDao;
import com.epam.oop.dao.db.exception.DbManagerException;
import com.epam.oop.dao.exception.DaoException;
import com.epam.oop.dao.db.ConnectionPool;
import com.epam.oop.dao.db.QueryExecutor;
import com.epam.oop.dao.db.exception.ConnectionPoolException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of DAO layer for sql database source.
 *
 * @author Uladzislau Seuruk.
 */
public class SqlNewsDao implements NewsDao {
    /**
     * Maximum number of displayed news.
     */
    private static final int MAX_RESULT_SIZE = 50;

    private static final String SQL_INSERT_NEWS =
            "INSERT INTO portal.news (category, title, date) VALUES (?, ?, ?)";

    private static final String SQL_SELECT_BY_CATEGORY =
            "SELECT category, title, `date` FROM portal.news WHERE category = ?";

    private static final String SQL_SELECT_BY_CATEGORY_OR_PART_IN_TITLE
            = "SELECT category, title, `date` FROM portal.news WHERE category = ? OR title LIKE ?";

    private static final String SQL_SELECT_PART_IN_TITLE
            = "SELECT category, title, `date` FROM portal.news WHERE title LIKE ?";

    private static final String SQL_SELECT_SINCE_DATE
            = "SELECT category, title, `date` FROM portal.news WHERE DATE(`date`) >= ?";

    @Override
    public void addNews(News news) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String[] params = { news.getCategory().toString(),
                                news.getTitle(),
                                news.getPublicationDate() };
            QueryExecutor.getInstance().executeUpdate(connection, SQL_INSERT_NEWS, params);
        } catch (ConnectionPoolException | DbManagerException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
    }

    @Override
    public List<News> getNews(String... tagArray) throws DaoException {
        Connection connection = null;
        Set<News> newsSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            for (String tag : tagArray) {
                String[] params = { tag, "%" + tag + "%" };
                Set<News> matchingSet = QueryExecutor.getInstance().executeSelect(
                        connection, SQL_SELECT_BY_CATEGORY_OR_PART_IN_TITLE, params);
                newsSet = findIntersection(newsSet, matchingSet);
            }
        } catch (ConnectionPoolException | DbManagerException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
        return limitResult(new ArrayList<>(newsSet));
    }

    @Override
    public List<News> getNewsByCategory(Category... categories) throws DaoException {
        Connection connection = null;
        Set<News> newsSet = new HashSet<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            for (Category category : categories) {
                Set<News> matchingSet = QueryExecutor.getInstance().executeSelect(
                        connection, SQL_SELECT_BY_CATEGORY, category.toString());
                newsSet.addAll(matchingSet);
            }
        } catch (ConnectionPoolException | DbManagerException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
        return limitResult(new ArrayList<>(newsSet));
    }

    @Override
    public List<News> getNewsByTitle(String... tags) throws DaoException {
        Connection connection = null;
        Set<News> newsSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            for (String tag : tags) {
                Set<News> matchingSet = QueryExecutor.getInstance().executeSelect(
                        connection, SQL_SELECT_PART_IN_TITLE, "%" + tag + "%");
                newsSet = findIntersection(newsSet, matchingSet);
            }
        } catch (ConnectionPoolException | DbManagerException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
        return limitResult(new ArrayList<>(newsSet));
    }

    @Override
    public List<News> getNewsSinceDate(String date) throws DaoException {
        Connection connection = null;
        Set<News> newsSet;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            newsSet = QueryExecutor.getInstance().executeSelect(
                    connection, SQL_SELECT_SINCE_DATE, date);
        } catch (ConnectionPoolException | DbManagerException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
        return limitResult(new ArrayList<>(newsSet));
    }

    private Set<News> findIntersection(Set<News> firstSet, Set<News> secondSet) {
        if (firstSet == null) {
            return secondSet;
        }
        if (firstSet.isEmpty()) {
            return firstSet;
        }
        Set<News> intersectionSet = new HashSet<>();
        for (News news : secondSet) {
            if (firstSet.contains(news)) {
                intersectionSet.add(news);
            }
        }
        return intersectionSet;
    }

    private List<News> limitResult(List<News> result) {
        if (result.size() <= MAX_RESULT_SIZE) {
            return result;
        }
        return result.subList(0, MAX_RESULT_SIZE);
    }
}