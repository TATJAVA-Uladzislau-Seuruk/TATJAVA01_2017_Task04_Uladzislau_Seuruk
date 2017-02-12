package com.epam.oop.dao.db.util;

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

    private DbResourceManager() {}

    /**
     * Closes received <tt>ResultSet</tt>.
     *
     * @param resultSet <tt>ResultSet</tt> to close.
     */
    public static void closeResultSet(ResultSet resultSet) {
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
    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.error("Statement was not closed.", e);
            }
        }
    }
}