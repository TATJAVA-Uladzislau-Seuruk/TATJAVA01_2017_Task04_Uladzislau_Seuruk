package com.epam.oop.dao.util;

/**
 * Contains constants for parsing news records.
 *
 * @author Uladzislau Seuruk.
 */
public class ParsingSymbols {
    /**
     * Symbol that indicates the start of news parameter.
     */
    public static final char PARAMETER_END = ']';
    /**
     * Symbol that indicates the start of news parameter.
     */
    public static final char PARAMETER_START = '[';
    /**
     * Symbol that indicates the end of news record.
     */
    public static final char RECORD_END_SYMBOL = '}';
    /**
     * Symbol that indicates the start of news record.
     */
    public static final char RECORD_START_SYMBOL = '{';
    /**
     * Symbol that separates parameter name from value.
     */
    public static final char VALUE_DELIMITER = '|';
}