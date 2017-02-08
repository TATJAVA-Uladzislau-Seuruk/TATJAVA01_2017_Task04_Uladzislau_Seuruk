package com.epam.oop.dao.factory;

import com.epam.oop.dao.NewsDao;
import com.epam.oop.dao.exception.DaoException;
import com.epam.oop.dao.impl.FileNewsDao;
import com.epam.oop.dao.impl.SqlNewsDao;

/**
 * Implements Factory design pattern.
 *
 * @author Uladzislau Seuruk.
 */
public class DaoFactory {
    /**
     * Singleton of this class.
     */
    private static DaoFactory instance = new DaoFactory();
    /**
     * Instance of file DAO.
     */
    private NewsDao newsFileDao = new FileNewsDao();
    /**
     * Instance of sql DAO.
     */
    private NewsDao sqlNewsDao;

    /**
     * Returns instance of this class.
     */
    public static DaoFactory getInstance() {
        return instance;
    }

    /**
     * Returns instance of file DAO.
     */
    public NewsDao getFileNewsDao() {
        return newsFileDao;
    }

    /**
     * Returns instance of sql DAO.
     */
    public NewsDao getSqlNewsDao() throws DaoException {
        if (sqlNewsDao == null) {
            sqlNewsDao = new SqlNewsDao();
        }
        return sqlNewsDao;
    }
}