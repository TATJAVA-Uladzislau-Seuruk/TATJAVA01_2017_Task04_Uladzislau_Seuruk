package com.epam.oop.dao.db;

import com.epam.oop.bean.Category;
import com.epam.oop.bean.News;
import com.epam.oop.dao.db.exception.DbManagerException;
import com.epam.oop.dao.db.util.DbResourceManager;

import java.sql.*;
import java.util.*;

/**
 * Makes queries to database.
 *
 * @author Uladzislau Seuruk.
 */
public class DbManager {
    /**
     * Array with parts of 'insert' query.
     */
    private static final String INSERT_STATEMENT[] = {
            "INSERT INTO ",
            " (category, title, date) VALUES (?, ?, ?)"
    };
    /**
     * Array with parts of 'select' query.
     */
    private static final String SELECT_STATEMENT[] = {
            "SELECT category, title, `date` FROM ",
            " WHERE ",
            "category LIKE ?",
            "title LIKE ?",
            "`date` LIKE ?"
    };
    /**
     * Array with sql logic operators.
     */
    private static final String LOGIC_OPERATORS[] = {
            " AND ",
            " OR ",
            " NOT ",
            " XOR "
    };
    /**
     * Maximum number of displayed news.
     */
    private static final int MAX_RESULT_SIZE = 50;
    /**
     * Instance of this class.
     */
    private static DbManager instance;

    private DbManager() {}

    /**
     * Returns instance of this class.
     */
    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    /**
     * Adds record about received news to database.
     *
     * @param connection <tt>Connection</tt> to database.
     * @param tableName <tt>String</tt> with name of table to add record into.
     * @param news <tt>News</tt> to add record about.
     * @throws DbManagerException if there were any trouble occurred while adding record.
     */
    public void addNews(Connection connection, String tableName, News news) throws DbManagerException {
        String query = INSERT_STATEMENT[0] + tableName + INSERT_STATEMENT[1];
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, news.getCategory().toString());
            ps.setString(2, news.getTitle());
            ps.setString(3, news.getPublicationDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbManagerException(e.getMessage(), e);
        } finally {
            if (ps != null) {
                DbResourceManager.closeStatement(ps);
            }
        }
    }

    public List<News> getNewsByCategory(Connection connection,
                                        String tableName,
                                        Category... categArray)
            throws DbManagerException {
        return null;
    }

    public List<News> getNewsByDate(Connection connection,
                                        String tableName,
                                        String... tagArray)
            throws DbManagerException {
        return null;
    }

    public List<News> getNewsByTitle(Connection connection,
                                    String tableName,
                                    String... tagArray)
            throws DbManagerException {
        return null;
    }

    /**
     * Get records from database that match received tags.
     *
     * @param connection <tt>Connection</tt> to database.
     * @param tableName <tt>String</tt> with name of table to add record into.
     * @param tagArray <tt>String</tt>s with tags.
     * @return <tt>List</tt> with <tt>News</tt> that match received tags.
     * @throws DbManagerException if there were any trouble occurred while finding matching records.
     */
    public List<News> getNewsByTags(Connection connection, String tableName, String... tagArray)
            throws DbManagerException {
        Set<News> newsSet = null;
        String originQuery = SELECT_STATEMENT[0] + tableName + SELECT_STATEMENT[1]
                + SELECT_STATEMENT[2] + LOGIC_OPERATORS[1]
                + SELECT_STATEMENT[3] + LOGIC_OPERATORS[1]
                + SELECT_STATEMENT[4];
        for (String tag : tagArray) {
            Set<News> matchingSet = getMatchingNews(connection, originQuery, tag);
            newsSet = findIntersection(newsSet, matchingSet);
        }
        ArrayList<News> result = new ArrayList<>(newsSet);
        if (result.size() > MAX_RESULT_SIZE) {
            return result.subList(0, MAX_RESULT_SIZE);
        }
        return result;
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

    private Set<News> findIntersection(Set<News> firstSet, Set<News> secondSet) {
        if (firstSet != null) {
            if (firstSet.isEmpty()) {
                return firstSet;
            }
            Set<News> intersectionSet = new HashSet<>();
            for (News news : secondSet) {
                if (firstSet.contains(news)) {
                    intersectionSet.add(news);
                }
            }
            return intersectionSet;
        }
        return secondSet;
    }

    private Set<News> getMatchingNews(Connection connection, String query, String tag)
            throws DbManagerException {
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + tag + "%");
            ps.setString(2, "%" + tag + "%");
            ps.setString(3, "%" + tag + "%");
            resultSet = ps.executeQuery();
            return convertToSet(resultSet);
        } catch (SQLException e) {
            throw new DbManagerException(e.getMessage(), e);
        } finally {
            DbResourceManager.closeResultSet(resultSet);
            DbResourceManager.closeStatement(ps);
        }
    }
}