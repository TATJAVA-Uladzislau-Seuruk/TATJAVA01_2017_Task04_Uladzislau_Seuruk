package com.epam.oop;

import com.epam.oop.view.View;

/**
 * @author Uladzislau Seuruk.
 */
public class Main {
    public static void main(String[] args) {
        View view = View.getInstance();
        view.start();
        view.makeRequest("find_news");
        view.makeRequest("command_that_might_be_failed category=film Title=\"\"");
        view.makeRequest("add_news Title=\"Second Earth\"");
        view.makeRequest("add_news category=film title=\"\"");
        view.makeRequest("add_news category=film title=\"");
        view.makeRequest("add_news category=film");
        view.makeRequest("add_news category=film title=\"Second Earth\"");
        view.makeRequest("find_news_by_category film");
        view.makeRequest("find_news_by_title 100");
        view.makeRequest("find_news 100 book");
        view.makeRequest("find_news_since_date 2017-7-21");
        view.finish();
    }
}