package com.epam.oop.dao.impl;

import com.epam.oop.bean.News;
import com.epam.oop.dao.NewsDao;
import com.epam.oop.dao.exception.DaoException;
import com.epam.oop.dao.util.db.ConnectionPool;
import com.epam.oop.dao.util.db.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * .
 *
 * @author Uladzislau Seuruk.
 */
public class SqlNewsDao implements NewsDao {
    private static final String DB_NAME = "portal";
    private static final String TABLE_NAME = "news";
    private static final String INSERT_STATEMENT = "INSERT INTO " + DB_NAME + "." + TABLE_NAME
            + " (category, title, date)" + " VALUES (?, ?, ?)";

    public SqlNewsDao() throws DaoException {
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException cpe) {
            throw new DaoException(cpe.getMessage(), cpe);
        }
    }

    @Override
    public void addNews(News news) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT);
            ps.setString(1, news.getCategory().toString());
            ps.setString(2, news.getTitle());
            ps.setString(3, news.getPublicationDate());
            ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
    }

    @Override
    public List<News> getNews(String... tags) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException cpe) {
            throw new DaoException(cpe.getMessage(), cpe);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
        return null;
    }
}