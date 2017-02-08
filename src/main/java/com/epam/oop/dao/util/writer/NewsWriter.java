package com.epam.oop.dao.util.writer;

import com.epam.oop.bean.News;
import com.epam.oop.dao.util.writer.exception.WritingException;

/**
 * Reads news data to some source.
 *
 * @author Uladzislau Seuruk.
 */
public interface NewsWriter {
    /**
     * Writes received news to the end of file.
     *
     * @param news <tt>News</tt> to write.
     * @throws WritingException if there were some troubles occurred while writing data to some source.
     */
    void writeToEnd(News news) throws WritingException;
}
