package com.epam.oop.dao.impl;

import com.epam.oop.bean.News;
import com.epam.oop.dao.NewsDao;
import com.epam.oop.dao.exception.DaoException;
import com.epam.oop.dao.util.reader.exception.ReadingException;
import com.epam.oop.dao.util.reader.impl.TxtReader;
import com.epam.oop.dao.util.writer.exception.WritingException;
import com.epam.oop.dao.util.writer.impl.TxtWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementation of DAO layer for text file source.
 *
 * @author Uladzislau Seuruk.
 */
public class TxtNewsDao implements NewsDao {
    /**
     * Default path to file.
     */
    private static final String DEFAULT_FILE_PATH = System.getProperty("user.dir")
            + File.separator + "data.txt";

    /**
     * @see NewsDao#addNews(News)
     */
    @Override
    public void addNews(News news) throws DaoException {
        try {
            new TxtWriter(DEFAULT_FILE_PATH).writeToEnd(news);
        } catch (WritingException we) {
            throw new DaoException(we.getMessage(), we);
        }
    }

    /**
     * @see NewsDao#getNews(String...)
     */
    @Override
    public List<News> getNews(String... tags) throws DaoException {
        try {
            Set<News> newsSet = new TxtReader(DEFAULT_FILE_PATH).read(tags);
            return new ArrayList<>(newsSet);
        } catch (ReadingException re) {
            throw new DaoException(re.getMessage(), re);
        }
    }
}