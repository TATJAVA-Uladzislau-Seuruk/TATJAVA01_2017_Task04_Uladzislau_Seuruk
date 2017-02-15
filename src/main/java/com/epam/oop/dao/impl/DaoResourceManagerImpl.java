package com.epam.oop.dao.impl;

import com.epam.oop.dao.DaoResourceManager;
import com.epam.oop.dao.exception.DaoException;
import com.epam.oop.dao.db.ConnectionPool;
import com.epam.oop.dao.db.exception.ConnectionPoolException;

/**
 * Implements <tt>DaoResourceManager</tt>.
 *
 * @author Uladzislau Seuruk.
 */
public class DaoResourceManagerImpl implements DaoResourceManager {
    @Override
    public void initResources() throws DaoException {
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException cpe) {
            throw new DaoException(cpe.getMessage(), cpe);
        }
    }

    @Override
    public void freeResources() {
        ConnectionPool.getInstance().dispose();
    }
}