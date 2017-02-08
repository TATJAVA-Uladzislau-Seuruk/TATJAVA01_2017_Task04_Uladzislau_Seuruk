package com.epam.oop.dao.util.reader.impl;

import com.epam.oop.bean.News;
import com.epam.oop.dao.util.ParsingSymbols;
import com.epam.oop.dao.util.parser.TxtNewsParser;
import com.epam.oop.dao.util.parser.exception.ItemParsingException;
import com.epam.oop.dao.util.reader.NewsReader;
import com.epam.oop.dao.util.reader.exception.ReadingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Reads news data from text file.
 *
 * @author Uladzislau Seuruk.
 */
public class TxtReader implements NewsReader {
    /**
     * File with data.
     */
    private File file;

    /**
     * Throws <tt>ReadingException</tt> if received <tt>File</tt> was not initialized.
     */
    public TxtReader(File file) throws ReadingException {
        if (file == null) {
            throw new ReadingException("File was not initialized.");
        }
        this.file = file;
    }

    /**
     * Throws <tt>ReadingException</tt> if received <tt>String</tt> with path to file was not
     * initialized.
     */
    public TxtReader(String filePath) throws ReadingException {
        if (filePath == null) {
            throw new ReadingException("String with path to file was not initialized.");
        }
        this.file = new File(filePath);
    }

    /**
     * @see NewsReader#read(String... args)
     */
    @Override
    public Set<News> read(String... args) throws ReadingException {
        if (!file.exists()) {
            throw new ReadingException("File was not found.");
        }
        Set<News> newsSet = new HashSet<>();
        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(String.valueOf(ParsingSymbols.RECORD_END_SYMBOL));
            TxtNewsParser parser = new TxtNewsParser();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                line = line.trim();
                if (isFit(line, args)) {
                    newsSet.add(parser.parse(line));
                }
            }
        } catch (FileNotFoundException | ItemParsingException e) {
            throw new ReadingException(e.getMessage(), e);
        }
        return newsSet;
    }

    private boolean isFit(String line, String... args) {
        for (String param : args) {
            if (!line.toLowerCase().contains(param.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}