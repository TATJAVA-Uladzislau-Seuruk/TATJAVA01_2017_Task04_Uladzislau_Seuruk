package com.epam.oop.dao.util.reader;

import com.epam.oop.bean.News;
import com.epam.oop.dao.util.reader.exception.ReadingException;

import java.util.Set;

/**
 * Reads news data from some source.
 *
 * @author Uladzislau Seuruk.
 */
public interface NewsReader {
    /**
     * Read news data that matches received criteria from file.
     *
     * @param args <tt>String</tt>s with search criteria.
     * @return <tt>Set</tt> with <tt>News</tt>.
     * @throws ReadingException if there was some troubles occurred while reading.
     */
    Set<News> read(String... args) throws ReadingException;
}