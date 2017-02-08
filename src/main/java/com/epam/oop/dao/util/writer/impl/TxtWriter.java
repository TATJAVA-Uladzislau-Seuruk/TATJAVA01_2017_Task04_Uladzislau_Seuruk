package com.epam.oop.dao.util.writer.impl;

import com.epam.oop.bean.News;
import com.epam.oop.bean.Tag;
import com.epam.oop.dao.util.ParsingSymbols;
import com.epam.oop.dao.util.writer.NewsWriter;
import com.epam.oop.dao.util.writer.exception.WritingException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;

/**
 * Adds new news record to file.
 *
 * @author Uladzislau Seuruk.
 */
public class TxtWriter implements NewsWriter {
    /**
     * File to write data into.
     */
    private File file;

    /**
     * Throws <tt>WritingException</tt> if received <tt>File</tt> was not initialized.
     */
    public TxtWriter(File file) throws WritingException {
        if (file == null) {
            throw new WritingException("File was not initialized.");
        }
        this.file = file;
    }

    /**
     * Throws <tt>WritingException</tt> if received <tt>String</tt> with path to file was not
     * initialized.
     */
    public TxtWriter(String filePath) throws WritingException {
        if (filePath == null) {
            throw new WritingException("String with path to file was not initialized.");
        }
        this.file = new File(filePath);
    }

    /***
     * @see NewsWriter#writeToEnd(News)
     */
    @Override
    public void writeToEnd(News news) throws WritingException {
        if (!file.exists()) {
            throw new WritingException("File was not found.");
        }
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file, true)))) {
            out.append(makeFormattedString(news));
        } catch (IOException e) {
            throw new WritingException(e.getMessage(), e);
        }
    }

    private StringBuilder appendParam(StringBuilder builder,
                                      String key,
                                      String value) {
        builder.append(ParsingSymbols.PARAMETER_START)
                .append(key)
                .append(ParsingSymbols.VALUE_DELIMITER)
                .append(value)
                .append(ParsingSymbols.PARAMETER_END);
        return builder;
    }

    private String makeFormattedString(News news) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n")
                .append(ParsingSymbols.RECORD_START_SYMBOL);
        builder = appendParam(builder, Tag.CATEGORY.toString(), news.getCategory().toString());
        builder = appendParam(builder, Tag.TITLE.toString(), news.getTitle());
        builder = appendParam(builder, Tag.DATE.toString(), news.getPublicationDate());
        builder.append(ParsingSymbols.RECORD_END_SYMBOL);
        return  builder.toString();
    }
}