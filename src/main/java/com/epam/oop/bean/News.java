package com.epam.oop.bean;

import java.io.Serializable;

/**
 * Provides information about single news.
 *
 * @author Uladzislau Seuruk.
 */
public class News implements Serializable {
    /**
     * Category of this news.
     */
    private Category category = null;
    /**
     * Publication date of this news.
     */
    private String publicationDate = null;
    /**
     * Title of this news.
     */
    private String title = null;

    public News() {}

    public News(Category category, String title, String date) {
        this.category = category;
        this.title = title;
        this.publicationDate = date;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }

        News news = (News) object;

        if (this.category == null) {
            if (news.category != null) {
                return false;
            }
        } else {
            if (!this.category.equals(news.category)) {
                return false;
            }
        }

        if (this.publicationDate == null) {
            if (news.publicationDate != null) {
                return false;
            }
        } else {
            if (!this.publicationDate.equals(news.publicationDate)) {
                return false;
            }
        }

        if (this.title == null) {
            return news.title == null;
        } else {
            return this.title.equals(news.title);
        }
    }

    /**
     * Getter.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Getter.
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * Getter.
     */
    public String getTitle() {
        return title;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + (category == null ? 0 : category.hashCode());
        hash = hash * 31 + (publicationDate == null ? 0 : publicationDate.hashCode());
        hash = hash * 13 + (title == null ? 0 : title.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + "category: " + category.toString() + ", publicationDate: "
                + publicationDate + ", title: " + title;
    }
}