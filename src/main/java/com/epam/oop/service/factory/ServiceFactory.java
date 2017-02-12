package com.epam.oop.service.factory;

import com.epam.oop.service.CatalogService;
import com.epam.oop.service.ServiceResourceManager;
import com.epam.oop.service.impl.CatalogServiceImpl;
import com.epam.oop.service.impl.ServiceResourceManagerImpl;

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
     * Instance of Service resource manager.
     */
    private ServiceResourceManager resourceManager = new ServiceResourceManagerImpl();

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

    /**
     * Returns instance of Service resource manager.
     */
    public ServiceResourceManager getResourceManager() {
        return resourceManager;
    }
}