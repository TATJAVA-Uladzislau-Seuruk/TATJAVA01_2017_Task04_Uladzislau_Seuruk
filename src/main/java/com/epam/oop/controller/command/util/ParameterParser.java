package com.epam.oop.controller.command.util;

import com.epam.oop.bean.Category;
import com.epam.oop.controller.command.util.exception.CategoryParsingException;
import com.epam.oop.controller.command.util.exception.ParameterParsingException;

import java.util.HashMap;
import java.util.Map;

/**
 * Parses Strings with parameters.
 *
 * @author Uladzislau Seuruk.
 */
public class ParameterParser {
    /**
     * Symbol that separates different parameters.
     */
    public static final char PARAMETER_DELIMITER = ' ';
    /**
     * Symbol that separates parameter name from value.
     */
    public static final char VALUE_DELIMITER = '=';
    /**
     * Symbol that marks parameter value that contains whitespaces.
     */
    public static final char VALUE_MARKER = '\"';

    /**
     * Parses received <tt>String</tt> with parameters.
     *
     * @param params <tt>String</tt> with command parameters.
     * @return <tt>Map</tt> with command parameters.
     * @throws ParameterParsingException if received <tt>String</tt> was not initialized.
     */
    public static Map<String, String> parse(String params) throws ParameterParsingException {
        if (params == null) {
            throw new ParameterParsingException("String with parameters was not initialized.");
        }
        Map<String, String> paramsMap = new HashMap<>();
        int valueDelimPosition = params.indexOf(VALUE_DELIMITER);
        while (valueDelimPosition != -1) {
            String option = params.substring(0, valueDelimPosition);
            params = params.substring(valueDelimPosition + 1);
            if (params.startsWith(String.valueOf(VALUE_MARKER))) {
                params = params.substring(1);
                int markerPosition = params.indexOf(VALUE_MARKER);
                if (markerPosition == -1) {
                    throw new ParameterParsingException("Illegal value marking.");
                }
                String value = params.substring(0, markerPosition);
                params = params.substring(markerPosition + 1);
                paramsMap.put(option.toUpperCase(), value);
            } else {
                String value = params;
                int paramsDelimPosition = params.indexOf(PARAMETER_DELIMITER);
                if (paramsDelimPosition != -1) {
                    value = params.substring(0, paramsDelimPosition);
                }
                params = params.substring(paramsDelimPosition + 1);
                paramsMap.put(option.toUpperCase(), value);
            }
            params = params.trim();
            valueDelimPosition = params.indexOf(VALUE_DELIMITER);
        }
        return paramsMap;
    }

    /**
     * Returns <tt>Category</tt> that corresponds received <tt>String</tt> with category's name.
     *
     * @param categoryParam <tt>String</tt> with name of requested category.
     * @return <tt>Category</tt> that corresponds received <tt>String</tt>.
     * @throws CategoryParsingException if received String wasn't initialized or does not match any
     * known category.
     */
    public static Category parseCategory(String categoryParam) throws CategoryParsingException {
        if (categoryParam == null) {
            throw new CategoryParsingException("String with category was not initialized.");
        }
        Category category;
        try {
            category = Category.valueOf(categoryParam.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CategoryParsingException("Unknown category.");
        }
        return category;
    }

    private ParameterParser() {}
}