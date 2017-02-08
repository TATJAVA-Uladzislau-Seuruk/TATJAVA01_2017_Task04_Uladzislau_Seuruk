package com.epam.oop.util.converter;

import com.epam.oop.bean.Category;
import com.epam.oop.util.converter.exception.ConversionException;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Converts text to other data types.
 *
 * @author Uladzislau Seuruk.
 */
public class TextConverter {
    /**
     * Converts received <tt>String</tt> to <tt>Category</tt>.
     *
     * @param request <tt>String</tt> with category.
     * @return <tt>Category</tt> that corresponds to received <tt>String</tt>.
     * @throws ConversionException if corresponded category was not found.
     */
    public Category convertToCategory(String request) throws ConversionException {
        if (request == null) {
            throw new ConversionException("String with category was not initialized.");
        }
        for (Category category : Category.values()) {
            if (request.toUpperCase().equals(category.toString())) {
                return category;
            }
        }
        throw new ConversionException("Unknown category.");
    }

    /**
     * Converts received <tt>String</tt> to <tt>Date</tt>.
     *
     * @param date <tt>String</tt> with date.
     * @return <tt>Date</tt> that matches the date from received <tt>String</tt>.
     * @throws ConversionException if received <tt>String</tt> has illegal format or matches
     * nonexistent date.
     */
    public Date convertToDate(String date) throws ConversionException {
        String[] parts = date.split("\\.");
        if (parts.length != 3) {
            throw new ConversionException("\"" + date + "\" - illegal date format.");
        }
        try {
            int year = Integer.valueOf(parts[2]);
            int month = Integer.valueOf(parts[1]);
            if (month < 1 || month > 12) {
                throw new ConversionException("\"" + date + "\" - illegal month value.");
            }
            int day = Integer.valueOf(parts[0]);
            if (day < 1 || day > 31) {
                throw new ConversionException("\"" + date + "\" - illegal day value.");
            }
            if (month == 2) {
                if (day > 28) {
                    if (day != 29 || year%4 != 0) {
                        throw new ConversionException("\"" + date + "\" - nonexistent date.");
                    }
                }
            }
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                if (day == 31) {
                    throw new ConversionException("\"" + date + " - nonexistent date.");
                }
            }
            GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
            return new Date(calendar.getTimeInMillis());
        } catch (NumberFormatException nfe) {
            throw new ConversionException("\"" + date + " - illegal date format.");
        }
    }
}