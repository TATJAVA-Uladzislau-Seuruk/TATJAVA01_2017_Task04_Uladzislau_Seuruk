package com.epam.oop.dao;

import com.epam.oop.dao.exception.DaoException;

/**
 * TODO.
 *
 * @author Uladzislau Seuruk.
 */
public interface DaoResourceManager {
    void initResources() throws DaoException;
    void freeResources();
}