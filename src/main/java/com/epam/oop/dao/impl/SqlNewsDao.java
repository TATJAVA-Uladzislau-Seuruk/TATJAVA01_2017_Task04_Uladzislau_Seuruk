package com.epam.oop.dao.impl;

import com.epam.oop.bean.Category;
import com.epam.oop.bean.News;
import com.epam.oop.dao.NewsDao;
import com.epam.oop.dao.db.util.DbResourceManager;
import com.epam.oop.dao.exception.DaoException;
import com.epam.oop.dao.db.ConnectionPool;
import com.epam.oop.dao.db.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of DAO layer for sql database source.
 *
 * @author Uladzislau Seuruk.
 */
public class SqlNewsDao implements NewsDao {
    /**
     * Maximum number of displayed news.
     */
    private static final int MAX_RESULT_SIZE = 50;

    private static final String SQL_INSERT_NEWS =
            "INSERT INTO portal.news (category, title, date) VALUES (?, ?, ?)";

    private static final String SQL_SELECT_BY_CATEGORY =
            "SELECT category, title, `date` FROM portal.news WHERE category = ?";

    private static final String SQL_SELECT_BY_CATEGORY_OR_PART_IN_TITLE
            = "SELECT category, title, `date` FROM portal.news WHERE category = ? OR title LIKE ?";

    private static final String SQL_SELECT_PART_IN_TITLE
            = "SELECT category, title, `date` FROM portal.news WHERE title LIKE ?";

    private static final String SQL_SELECT_SINCE_DATE
            = "SELECT category, title, `date` FROM portal.news WHERE DATE(`date`) >= ?";

    @Override
    public void addNews(News news) throws DaoException {
        PreparedStatement ps = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ps = connection.prepareStatement(SQL_INSERT_NEWS);
            ps.setString(1, news.getCategory().toString());
            ps.setString(2, news.getTitle());
            ps.setString(3, news.getPublicationDate());
            ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            if (ps != null) {
                DbResourceManager.closeStatement(ps);
            }
        }
    }

    @Override
    public List<News> getNews(String[] tagArray) throws DaoException {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Set<News> newsSet = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            for (String tag : tagArray) {
                ps = connection.prepareStatement(SQL_SELECT_BY_CATEGORY_OR_PART_IN_TITLE);
                ps.setString(1, tag);
                ps.setString(2, "%" + tag + "%");
                resultSet = ps.executeQuery();
                Set<News> matchingSet = convertToSet(resultSet);
                newsSet = findIntersection(newsSet, matchingSet);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            DbResourceManager.closeResultSet(resultSet);
            DbResourceManager.closeStatement(ps);
        }

        if (newsSet == null) {
            newsSet = new HashSet<>();
        }
        return limitResult(new ArrayList<>(newsSet));
    }

    @Override
    public List<News> getNewsByCategory(Category category) throws DaoException {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Set<News> newsSet = new HashSet<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
                ps = connection.prepareStatement(SQL_SELECT_BY_CATEGORY);
                ps.setString(1, category.toString());
                resultSet = ps.executeQuery();
                Set<News> matchingSet = convertToSet(resultSet);
                newsSet.addAll(matchingSet);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            DbResourceManager.closeResultSet(resultSet);
            DbResourceManager.closeStatement(ps);
        }

        return limitResult(new ArrayList<>(newsSet));
    }

    @Override
    public List<News> getNewsByTitle(String[] tags) throws DaoException {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Set<News> newsSet = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            for (String tag : tags) {
                ps = connection.prepareStatement(SQL_SELECT_PART_IN_TITLE);
                ps.setString(1, "%" + tag + "%");
                resultSet = ps.executeQuery();
                Set<News> matchingSet = convertToSet(resultSet);
                newsSet = findIntersection(newsSet, matchingSet);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            DbResourceManager.closeResultSet(resultSet);
            DbResourceManager.closeStatement(ps);
        }
        if (newsSet == null) {
            newsSet = new HashSet<>();
        }
        return limitResult(new ArrayList<>(newsSet));
    }

    @Override
    public List<News> getNewsSinceDate(String date) throws DaoException {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Set<News> newsSet;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ps = connection.prepareStatement(SQL_SELECT_SINCE_DATE);
            ps.setString(1, date);
            resultSet = ps.executeQuery();
            newsSet = convertToSet(resultSet);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            DbResourceManager.closeResultSet(resultSet);
            DbResourceManager.closeStatement(ps);
        }
        return limitResult(new ArrayList<>(newsSet));
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
        if (firstSet == null) {
            return secondSet;
        }
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

    private List<News> limitResult(List<News> result) {
        if (result.size() <= MAX_RESULT_SIZE) {
            return result;
        }
        return result.subList(0, MAX_RESULT_SIZE);
    }
}