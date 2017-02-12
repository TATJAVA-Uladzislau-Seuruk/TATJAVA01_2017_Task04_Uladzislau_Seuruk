package com.epam.oop.dao.db.properties;

import java.util.ResourceBundle;

/**
 * Repository for properties values.
 *
 * @author Uladzislau Seuruk.
 */
public class DbPropertiesHandler {
    /**
     * Instance of this class.
     */
    private static DbPropertiesHandler instance = new DbPropertiesHandler();
    /**
     * Repository with properties values.
     */
    private ResourceBundle bundle = ResourceBundle.getBundle("db");

    private DbPropertiesHandler() {}

    /**
     * Returns instance of this class.
     */
    public static DbPropertiesHandler getInstance() {
        return instance;
    }

    /**
     * Gets a property value for the received key.
     *
     * @param key the key for the property.
     * @return the string with requested property.
     */
    public String getValue(String key) {
        return bundle.getString(key);
    }
}