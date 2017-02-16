package com.epam.oop.service.impl;

import com.epam.oop.dao.DaoResourceManager;
import com.epam.oop.dao.exception.DaoException;
import com.epam.oop.dao.factory.DaoFactory;
import com.epam.oop.service.ServiceResourceManager;
import com.epam.oop.service.exception.ServiceException;

/**
 * Implements <tt>ServiceResourceManager</tt>.
 *
 * @author Uladzislau Seuruk.
 */
public class ServiceResourceManagerImpl implements ServiceResourceManager {
    @Override
    public void initResources() throws ServiceException {
        try {
            DaoResourceManager manager = DaoFactory.getInstance().getResourceManager();
            manager.initResources();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void freeResources() {
        DaoResourceManager manager = DaoFactory.getInstance().getResourceManager();
        manager.freeResources();
    }
}