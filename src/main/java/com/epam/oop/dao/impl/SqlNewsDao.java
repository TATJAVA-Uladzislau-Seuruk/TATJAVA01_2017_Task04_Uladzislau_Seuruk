package com.epam.oop.dao.impl;

import com.epam.oop.bean.Category;
import com.epam.oop.bean.News;
import com.epam.oop.dao.NewsDao;
import com.epam.oop.dao.db.exception.DbManagerException;
import com.epam.oop.dao.exception.DaoException;
import com.epam.oop.dao.db.ConnectionPool;
import com.epam.oop.dao.db.DbManager;
import com.epam.oop.dao.db.exception.ConnectionPoolException;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation of DAO layer for sql database source.
 *
 * @author Uladzislau Seuruk.
 */
public class SqlNewsDao implements NewsDao {
    /**
     * Name of using database.
     */
    private static final String DB_NAME = "portal";
    /**
     * Name of using table.
     */
    private static final String TABLE_NAME = "news";

    @Override
    public void addNews(News news) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            DbManager.getInstance().addNews(connection,
                                            DB_NAME + '.' + TABLE_NAME,
                                            news);
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
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return DbManager.getInstance().getNewsByTags(connection,
                                                         DB_NAME + '.' + TABLE_NAME,
                                                         tagArray);
        } catch (ConnectionPoolException | DbManagerException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
    }

    @Override
    public List<News> getNewsByCategory(Category... categories) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return DbManager.getInstance().getNewsByCategory(connection,
                    DB_NAME + '.' + TABLE_NAME,
                    categories);
        } catch (ConnectionPoolException | DbManagerException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
    }

    @Override
    public List<News> getNewsByDate(String... dates) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return DbManager.getInstance().getNewsByDate(connection,
                    DB_NAME + '.' + TABLE_NAME,
                    dates);
        } catch (ConnectionPoolException | DbManagerException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
    }

    @Override
    public List<News> getNewsByTitle(String... tags) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return DbManager.getInstance().getNewsByTitle(connection,
                    DB_NAME + '.' + TABLE_NAME,
                    tags);
        } catch (ConnectionPoolException | DbManagerException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
    }
}