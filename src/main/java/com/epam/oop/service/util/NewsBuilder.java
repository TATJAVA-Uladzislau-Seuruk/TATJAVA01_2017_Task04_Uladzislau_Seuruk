package com.epam.oop.service.util;

import com.epam.oop.bean.Category;
import com.epam.oop.bean.News;
import com.epam.oop.service.util.exception.NewsBuildException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

/**
 * Parses news parameters.
 *
 * @author Uladzislau Seuruk.
 */
public class NewsBuilder {
    private static final Logger LOG = LogManager.getRootLogger();

    /**
     * Build new <tt>News</tt> with received parameters.
     *
     * @param category <tt>News</tt> <tt>Category</tt>.
     * @param title <tt>String</tt> with <tt>News</tt> title.
     * @return <tt>News</tt> with corresponded parameters.
     * @throws NewsBuildException if some troubles were occurred during build.
     */
    public News build(Category category, String title) throws NewsBuildException {
        if (category == null || title == null) {
            throw new NewsBuildException("At least one or received parameters was not initialized.");
        }
        if (title.isEmpty()) {
            throw new NewsBuildException("Received title is empty.");
        }
        String currentDate = formatDate(new Date());
        if (LOG.isDebugEnabled()) {
            LOG.debug(title + " : " + category + ", " + title + ", " + currentDate);
        }
        return new News(category, title, currentDate);
    }

    private String formatDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        StringBuilder builder = new StringBuilder();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        builder.append(calendar.get(Calendar.YEAR))
                .append("-")
                .append(formatSmallValue(month))
                .append("-")
                .append(formatSmallValue(day));
        if (LOG.isDebugEnabled()) {
            LOG.debug(date.toString() + " : " + builder.toString());
        }
        return builder.toString();
    }

    private String formatSmallValue(int value) {
        if (value < 10) {
            return "0" + value;
        } else {
            return "" + value;
        }
    }
}