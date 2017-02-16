package com.epam.oop.dao.impl;

import com.epam.oop.bean.Category;
import com.epam.oop.bean.News;
import com.epam.oop.dao.db.util.DbResourceManager;
import com.epam.oop.dao.factory.DaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import java.sql.*;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Uladzislau Seuruk.
 */
public class SqlNewsDaoTest {
    private static final Logger LOG = LogManager.getRootLogger();

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_PASSWORD = "123456";
    private static final String DB_URL = "jdbc:mysql://localhost:3306?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";

    private static final String SQL_DELETE =
            "DELETE FROM portal.news WHERE category = 'BOOK'" +
                    " AND title = 'Thinking in Java' AND `date` = '2017-01-01'";
    private static final String SQL_INSERT =
            "INSERT INTO portal.news (category, title, date)" +
                    " VALUES ('BOOK', 'Thinking in Java', '2017-01-01')";

    private Connection connection = null;

    @BeforeClass
    public void setUpClass() throws Exception {
        DaoFactory.getInstance().getResourceManager().initResources();
        Class.forName(DB_DRIVER);
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        executeQuery(connection, SQL_INSERT);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        executeQuery(connection, SQL_DELETE);
    }

    @AfterClass
    public void tearDownClass() throws Exception {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        DaoFactory.getInstance().getResourceManager().freeResources();
    }

    @Test
    public void negativeGetNewsByCategoryTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        List<News> result = new SqlNewsDao().getNewsByCategory(Category.DISK);
        assertFalse(result.contains(toFind));
    }

    @Test
    public void negativeGetNewsByExtendedTitleTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"Thinking in Java", "for Dummies"};
        List<News> result = new SqlNewsDao().getNewsByTitle(params);
        assertFalse(result.contains(toFind));
    }

    @Test
    public void negativeGetNewsByPartlyMatchingTitleTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"Writing", "in Java"};
        List<News> result = new SqlNewsDao().getNewsByTitle(params);
        assertFalse(result.contains(toFind));
    }

    @Test
    public void negativeGetNewsExtendedTitleTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"Thinking in Java", "for Dummies"};
        List<News> result = new SqlNewsDao().getNews(params);
        assertFalse(result.contains(toFind));
    }

    @Test
    public void negativeGetNewsPartlyMatchingTitleTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"Writing", "in Java"};
        List<News> result = new SqlNewsDao().getNews(params);
        assertFalse(result.contains(toFind));
    }

    @Test
    public void negativeGetNewsSinceLaterDateTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        List<News> result = new SqlNewsDao().getNewsSinceDate("2017-01-02");
        assertFalse(result.contains(toFind));
    }

    @Test
    public void negativeGetNewsWrongCategoryTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"Thinking in Java", "DISK"};
        List<News> result = new SqlNewsDao().getNews(params);
        assertFalse(result.contains(toFind));
    }

    @Test
    public void positiveAddNewsTest() throws Exception {
        executeQuery(connection, SQL_DELETE);
        News toAdd = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        SqlNewsDao dao = new SqlNewsDao();
        dao.addNews(toAdd);
        String[] params = new String[] {"Thinking in Java"};
        List<News> newsList = dao.getNewsByTitle(params);
        assertTrue(newsList.contains(toAdd));
    }

    @Test
    public void positiveGetNewsByCategoryTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        List<News> result = new SqlNewsDao().getNewsByCategory(Category.BOOK);
        assertTrue(result.contains(toFind));
    }

    @Test
    public void positiveGetNewsByExactTitleTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"Thinking in Java"};
        List<News> result = new SqlNewsDao().getNewsByTitle(params);
        assertTrue(result.contains(toFind));
    }

    @Test
    public void positiveGetNewsByPartOfTitleTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"in Java"};
        List<News> result = new SqlNewsDao().getNewsByTitle(params);
        assertTrue(result.contains(toFind));
    }

    @Test
    public void positiveGetNewsByTitleRegisterTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"IN JAVA"};
        List<News> result = new SqlNewsDao().getNewsByTitle(params);
        assertTrue(result.contains(toFind));
    }

    @Test
    public void positiveGetNewsCategoryTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"BOOK"};
        List<News> result = new SqlNewsDao().getNews(params);
        assertTrue(result.contains(toFind));
    }

    @Test
    public void positiveGetNewsExactTitleTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"Thinking in Java"};
        List<News> result = new SqlNewsDao().getNews(params);
        assertTrue(result.contains(toFind));
    }

    @Test
    public void positiveGetNewsMixedTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"Think", "in", "BOOK"};
        List<News> result = new SqlNewsDao().getNews(params);
        assertTrue(result.contains(toFind));
    }

    @Test
    public void positiveGetNewsRegisterTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"THINKING", "IN", "book"};
        List<News> result = new SqlNewsDao().getNews(params);
        assertTrue(result.contains(toFind));
    }

    @Test
    public void positiveGetNewsSinceEarlierDateTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        List<News> result = new SqlNewsDao().getNewsSinceDate("2016-12-31");
        assertTrue(result.contains(toFind));
    }

    @Test
    public void positiveGetNewsSinceExactDateTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        List<News> result = new SqlNewsDao().getNewsSinceDate("2017-01-01");
        assertTrue(result.contains(toFind));
    }

    @Test
    public void positiveGetNewsTitlePartsTest() throws Exception {
        News toFind = new News(Category.BOOK, "Thinking in Java", "2017-01-01");
        String[] params = new String[] {"Think", "Java"};
        List<News> result = new SqlNewsDao().getNews(params);
        assertTrue(result.contains(toFind));
    }

    private void executeQuery(Connection connection, String query) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            DbResourceManager.closeStatement(statement);
        }
    }
}