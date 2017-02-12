package com.epam.oop.service;

import com.epam.oop.service.exception.ServiceException;

/**
 * TODO.
 *
 * @author Uladzislau Seuruk.
 */
public interface ServiceResourceManager {
    void initResources() throws ServiceException;
    void freeResources();
}