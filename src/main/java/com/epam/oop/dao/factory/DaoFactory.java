package com.epam.oop.dao.factory;

import com.epam.oop.dao.DaoResourceManager;
import com.epam.oop.dao.NewsDao;
import com.epam.oop.dao.exception.DaoException;
import com.epam.oop.dao.impl.DaoResourceManagerImpl;
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
     * Instance of DAO resource manager.
     */
    private DaoResourceManager resourceManager = new DaoResourceManagerImpl();
    /**
     * Instance of News DAO.
     */
    private NewsDao sqlNewsDao = new SqlNewsDao();

    /**
     * Returns instance of this class.
     */
    public static DaoFactory getInstance() {
        return instance;
    }

    /**
     * Returns instance of News DAO.
     */
    public NewsDao getNewsDao() throws DaoException {
        return sqlNewsDao;
    }

    /**
     * Returns instance of DAO resource manager.
     */
    public DaoResourceManager getResourceManager() {
        return resourceManager;
    }
}