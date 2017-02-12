package com.epam.oop.controller.command.util;

import com.epam.oop.bean.News;

import java.util.List;

/**
 * Generates report based on found news.
 *
 * @author Uladzislau Seuruk.
 */
public class SearchReportGenerator {
    private SearchReportGenerator() {}

    /**
     * Generates report based on found news.
     *
     * @param newsList <tt>List</tt> with found news.
     * @return <tt>String</tt> with report about found news.
     */
    public static String generateReport(List<News> newsList) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < newsList.size(); ++i) {
            addNewsInfo(builder, newsList.get(i), i+1);
        }
        return "Found news:" + builder.toString();
    }

    private static void addNewsInfo(StringBuilder builder, News news, int number) {
        builder.append("\n")
                .append(number)
                .append(") \"")
                .append(news.getTitle())
                .append("\" Category: \"")
                .append(news.getCategory())
                .append("\" Publication Date: ")
                .append(news.getPublicationDate());
    }
}