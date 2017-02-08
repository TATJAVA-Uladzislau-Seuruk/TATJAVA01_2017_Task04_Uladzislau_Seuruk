package com.epam.oop.dao.util.parser;

import com.epam.oop.bean.Category;
import com.epam.oop.bean.News;
import com.epam.oop.bean.Tag;
import com.epam.oop.dao.util.ParsingSymbols;
import com.epam.oop.util.converter.TextConverter;
import com.epam.oop.util.converter.exception.ConversionException;
import com.epam.oop.dao.util.parser.exception.ItemParsingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Parses text files for news data.
 *
 * @author Uladzislau Seuruk.
 */
public class TxtNewsParser {
    private static final Logger LOG = LogManager.getRootLogger();
    /**
     * Parses received <tt>String</tt> for news.
     *
     * @return <tt>News</tt> with corresponded parameters.
     * @throws ItemParsingException if some troubles were occurred while parsing.
     */
    public News parse(String params) throws ItemParsingException {
        if (params == null) {
            throw new ItemParsingException("String with parameters was not initialized.");
        }
        if (!params.startsWith(String.valueOf(ParsingSymbols.RECORD_START_SYMBOL))
                || !params.endsWith(String.valueOf(ParsingSymbols.RECORD_END_SYMBOL))) {
            throw new ItemParsingException("Data corruption.");
        }
        params = params.substring(1, params.length() - 1);
        Map<String, String> paramsMap = makeParametersMap(params);
        String categoryParam = paramsMap.get(Tag.CATEGORY.toString());
        if (categoryParam == null) {
            throw new ItemParsingException("Category is missing.");
        }
        try {
            Category category = new TextConverter().convertToCategory(categoryParam);
            String title = paramsMap.get(Tag.TITLE.toString());
            if (title == null) {
                throw new ItemParsingException("Title is missing.");
            }
            String date = paramsMap.get(Tag.DATE.toString());
            if (date == null) {
                throw new ItemParsingException("Date is missing.");
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug(params + " : " + category + ", " + title + ", " + date);
            }
            return new News(category, title, date);
        } catch (ConversionException ce) {
            throw new ItemParsingException(ce.getMessage(), ce);
        }
    }

    private Map<String, String> makeParametersMap(String line) throws ItemParsingException  {
        Map<String, String> paramsMap = new HashMap<>();
        while (!line.isEmpty()) {
            int index = line.indexOf(ParsingSymbols.PARAMETER_END);
            String param = line.substring(0, index + 1);
            String[] values = parseParameter(param);
            paramsMap.put(values[0].toUpperCase(), values[1]);
            line = line.substring(index + 1);
        }
        return paramsMap;
    }

    private String[] parseParameter(String line) throws ItemParsingException {
        if (!line.startsWith(String.valueOf(ParsingSymbols.PARAMETER_START))) {
            throw new ItemParsingException("Data corruption");
        }
        if (!line.endsWith(String.valueOf(ParsingSymbols.PARAMETER_END))) {
            throw new ItemParsingException("Data corruption");
        }
        String[] values = line.split("\\" + String.valueOf(ParsingSymbols.VALUE_DELIMITER));
        values[0] = values[0].substring(1);
        values[1] = values[1].substring(0, values[1].length() - 1);
        return values;
    }
}