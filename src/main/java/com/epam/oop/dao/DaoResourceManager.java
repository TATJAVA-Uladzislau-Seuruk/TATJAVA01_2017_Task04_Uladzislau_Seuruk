package com.epam.oop.dao;

import com.epam.oop.dao.exception.DaoException;

/**
 * Provides interface for necessary layer resources initialization and release.
 *
 * @author Uladzislau Seuruk.
 */
public interface DaoResourceManager {
    /**
     * Initializes necessary layer resources. Might be called before any other functional method.
     *
     * @throws DaoException if there were some troubles occurred while resources initialization.
     */
    void initResources() throws DaoException;

    /**
     * Releases all necessary layer resources. Might be called after last functional method and
     * before closing program.
     */
    void freeResources();
}