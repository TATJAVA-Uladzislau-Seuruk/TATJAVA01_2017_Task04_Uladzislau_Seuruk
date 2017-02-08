package com.epam.oop.service.factory;

import com.epam.oop.service.CatalogService;
import com.epam.oop.service.impl.CatalogServiceImpl;

/**
 * Implements Factory design pattern.
 *
 * @author Uladzislau Seuruk.
 */
public class ServiceFactory {
    /**
     * Singleton of this class.
     */
    private static ServiceFactory instance = new ServiceFactory();
    /**
     * Instance of Service layer.
     */
    private CatalogService catalogServiceImpl = new CatalogServiceImpl();

    /**
     * Returns instance of this class.
     */
    public static ServiceFactory getInstance() {
        return instance;
    }

    /**
     * Returns instance of Service layer.
     */
    public CatalogService getCatalogService() {
        return catalogServiceImpl;
    }
}