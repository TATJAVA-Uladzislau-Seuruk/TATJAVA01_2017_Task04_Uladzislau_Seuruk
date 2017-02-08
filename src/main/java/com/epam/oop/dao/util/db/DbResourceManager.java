package com.epam.oop.dao.util.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages database resources.
 *
 * @author Uladzislau Seuruk.
 */
public class DbResourceManager {
    private static final Logger LOG = LogManager.getRootLogger();

    /**
     * Instance of this class.
     */
    private static DbResourceManager instance = new DbResourceManager();

    private DbResourceManager() {}

    /**
     * Returns instance of this class.
     */
    public static DbResourceManager getInstance() {
        return instance;
    }

    /**
     * Closes received <tt>ResultSet</tt>.
     *
     * @param resultSet <tt>ResultSet</tt> to close.
     */
    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOG.error("Result set was not closed.", e);
            }
        }
    }

    /**
     * Closes received <tt>Statement</tt>.
     *
     * @param statement <tt>Statement</tt> to close.
     */
    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.error("Statement was not closed.", e);
            }
        }
    }
}