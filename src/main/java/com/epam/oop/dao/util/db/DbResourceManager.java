package com.epam.oop.dao.util.db;

import java.util.ResourceBundle;

/**
 * .
 *
 * @author Uladzislau Seuruk.
 */
public class DbResourceManager {
    private static DbResourceManager instance = new DbResourceManager();

    private DbResourceManager() {}

    private ResourceBundle bundle = ResourceBundle.getBundle("db");

    public static DbResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}