package com.epam.oop.service;

import com.epam.oop.service.exception.ServiceException;

/**
 * Provides interface for necessary layer resources initialization and release.
 *
 * @author Uladzislau Seuruk.
 */
public interface ServiceResourceManager {
    /**
     * Initializes necessary layer resources. Might be called before any other functional method.
     *
     * @throws ServiceException if there were some troubles occurred while resources initialization.
     */
    void initResources() throws ServiceException;

    /**
     * Releases necessary layer resources. Might be called after last functional method and
     * before closing program.
     */
    void freeResources();
}