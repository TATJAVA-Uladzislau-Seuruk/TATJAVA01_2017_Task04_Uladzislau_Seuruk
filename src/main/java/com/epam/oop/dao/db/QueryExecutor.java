package com.epam.oop.dao.db;

import com.epam.oop.bean.Category;
import com.epam.oop.bean.News;
import com.epam.oop.dao.db.exception.DbManagerException;
import com.epam.oop.dao.db.util.DbResourceManager;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Executes queries to database.
 *
 * @author Uladzislau Seuruk.
 */
public class QueryExecutor {
    /**
     * Instance of this class.
     */
    private static QueryExecutor instance;

    /**
     * Returns instance of this class.
     */
    public static QueryExecutor getInstance() {
        if (instance == null) {
            instance = new QueryExecutor();
        }
        return instance;
    }

    private QueryExecutor() {}

    /**
     * Executes received query that might update database data.
     *
     * @param connection <tt>Connection</tt> to database.
     * @param query <tt>String</tt> with sql query.
     * @param values <tt>String</tt>s with parameters for prepared statement.
     * @throws DbManagerException if there were any trouble occurred while adding record.
     */
    public void executeUpdate(Connection connection, String query, String... values)
            throws DbManagerException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            int index = 1;
            for (String value : values) {
                ps.setString(index, value);
                ++index;
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbManagerException(e.getMessage(), e);
        } finally {
            if (ps != null) {
                DbResourceManager.closeStatement(ps);
            }
        }
    }

    /**
     * Executes received select query.
     *
     * @param connection <tt>Connection</tt> to database.
     * @param query <tt>String</tt> with sql query.
     * @param values <tt>String</tt>s with parameters for prepared statement.
     * @return <tt>Set</tt> with <tt>News</tt> that match received query.
     * @throws DbManagerException
     */
    public Set<News> executeSelect(Connection connection, String query, String... values)
            throws DbManagerException {
        Set<News> newsSet;
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            for (int i = 0; i < values.length; ++i) {
                ps.setString(i + 1, values[i]);
            }
            resultSet = ps.executeQuery();
            newsSet = convertToSet(resultSet);
        } catch (SQLException e) {
            throw new DbManagerException(e.getMessage(), e);
        } finally {
            DbResourceManager.closeResultSet(resultSet);
            DbResourceManager.closeStatement(ps);
        }
        return newsSet;
    }

    private Set<News> convertToSet(ResultSet resultSet) throws SQLException {
        Set<News> newsSet = new HashSet<>();
        while (resultSet.next()) {
            Category category = Category.valueOf(resultSet.getString(1));
            News news = new News(category,
                                 resultSet.getString(2),
                                 resultSet.getString(3));
            newsSet.add(news);
        }
        return newsSet;
    }
}