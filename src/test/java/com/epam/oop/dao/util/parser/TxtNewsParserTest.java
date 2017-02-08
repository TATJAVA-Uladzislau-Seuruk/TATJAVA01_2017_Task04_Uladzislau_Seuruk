package com.epam.oop.dao.util.parser;

import com.epam.oop.bean.Category;
import com.epam.oop.bean.News;
import com.epam.oop.dao.util.parser.exception.ItemParsingException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * @author Uladzislau Seuruk.
 */
public class TxtNewsParserTest {
    private TxtNewsParser parser;

    @BeforeTest
    public void setUp() {
        parser = new TxtNewsParser();
    }

    @DataProvider (name = "corruptedData")
    public Object[][] getCorruptedData() {
        return new Object[][] {
                {null},
                {""},
                {"{[********|DISK][TITLE|Bible][DATE|01.01.0001]}"},
                {"{[CATEGORY|DISK][*****|Bible][DATE|01.01.0001]}"},
                {"{[CATEGORY|DISK][TITLE|Bible][****|01.01.0001]}"},
                {"{[********|DISK][*****|Bible][DATE|01.01.0001]}"},
                {"{[********|DISK][TITLE|Bible][****|01.01.0001]}"},
                {"[CATEGORY|DISK][*****|Bible][****|01.01.0001]}"},
                {"{[********|DUMMY][*****|Bible][****|01.01.0001]}"},
                {"{[TITLE|Bible][DATE|01.01.0001]}"},
                {"{[CATEGORY|DISK][DATE|01.01.0001]}"},
                {"{[CATEGORY|DISK][TITLE|Bible]}"},
                {"{[DATE|01.01.0001]}"},
                {"{[TITLE|Bible]}"},
                {"{[CATEGORY|DISK]}"},
                {"CATEGORY|DISK TITLE|Bible DATE|01.01.0001"}
        };
    }

    @Test (expectedExceptions = ItemParsingException.class, dataProvider = "corruptedData")
    public void negativeParseDataCorruptionTest(String data) throws Exception {
        News news1 = new News(Category.BOOK, "Bible", "01.01.0001");
        News news2 = parser.parse(data);
        assertNotEquals(news1, news2);
    }

    @Test
    public void negativeParseDifferentCategoryTest() throws Exception {
        News news1 = new News(Category.BOOK, "Bible", "01.01.0001");
        News news2 = parser.parse("{[CATEGORY|DISK][TITLE|Bible][DATE|01.01.0001]}");
        assertNotEquals(news1, news2);
    }

    @Test
    public void negativeParseDifferentDateTest() throws Exception {
        News news1 = new News(Category.BOOK, "Bible", "01.01.0001");
        News news2 = parser.parse("{[CATEGORY|BOOK][TITLE|Bible][DATE|01.01.2011]}");
        assertNotEquals(news1, news2);
    }

    @Test
    public void negativeParseDifferentTitleTest() throws Exception {
        News news1 = new News(Category.BOOK, "Bible", "01.01.0001");
        News news2 = parser.parse("{[CATEGORY|BOOK][TITLE|Koran][DATE|01.01.0001]}");
        assertNotEquals(news1, news2);
    }

    @Test (expectedExceptions = ItemParsingException.class)
    public void negativeParseUnknownCategoryTest() throws Exception {
        News news1 = new News(Category.BOOK, "Bible", "01.01.0001");
        News news2 = parser.parse("{[CATEGORY|DUMMY][TITLE|Bible][DATE|01.01.0001]}");
        assertNotEquals(news1, news2);
    }

    @Test
    public void positiveParseTest() throws Exception {
        News news1 = new News(Category.BOOK, "Bible", "01.01.0001");
        News news2 = parser.parse("{[CATEGORY|BOOK][TITLE|Bible][DATE|01.01.0001]}");
        assertEquals(news1, news2);
    }
}