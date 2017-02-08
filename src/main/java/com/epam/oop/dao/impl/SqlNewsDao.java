package com.epam.oop.dao.impl;

import com.epam.oop.bean.News;
import com.epam.oop.dao.NewsDao;
import com.epam.oop.dao.exception.DaoException;
import com.epam.oop.dao.util.db.ConnectionPool;
import com.epam.oop.dao.util.db.DbManager;
import com.epam.oop.dao.util.db.exception.ConnectionPoolException;

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

    /**
     * Initializes connection pool with connections.
     *
     * @throws DaoException if there were some troubles occurred while initialization.
     */
    public SqlNewsDao() throws DaoException {
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException cpe) {
            throw new DaoException(cpe.getMessage(), cpe);
        }
    }

    /**
     * @see NewsDao#addNews(News)
     */
    @Override
    public void addNews(News news) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            DbManager.getInstance().addNews(connection,
                                            DB_NAME + '.' + TABLE_NAME,
                                            news);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
    }

    /**
     * @see NewsDao#getNews(String...)
     */
    @Override
    public List<News> getNews(String... tagArray) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return DbManager.getInstance().getNewsByTags(connection,
                                                         DB_NAME + '.' + TABLE_NAME,
                                                         tagArray);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
    }
}