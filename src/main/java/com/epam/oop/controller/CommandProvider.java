package com.epam.oop.controller;

import com.epam.oop.controller.command.Command;
import com.epam.oop.controller.command.impl.AddNews;
import com.epam.oop.controller.command.impl.FindNews;
import com.epam.oop.controller.command.impl.WrongRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Repository for known commands.
 *
 * @author Uladzislau Seuruk.
 */
final class CommandProvider {
    /**
     * Map with known commands.
     */
    private final Map<String, Command> repository = new HashMap<>();

    CommandProvider() {
        repository.put(AddNews.COMMAND_NAME, new AddNews());
        repository.put(FindNews.COMMAND_NAME, new FindNews());
        repository.put(WrongRequest.COMMAND_NAME, new WrongRequest());
    }

    /**
     * Returns <tt>Command</tt> that corresponds received command name.
     *
     * @param name name of required command.
     * @return requested <tt>Command</tt> or <tt>WrongCommand</tt> if requested command is unknown.
     */
    Command getCommand(String name) {
        if (name == null) {
            return repository.get(WrongRequest.COMMAND_NAME);
        }
        Command command = repository.get(name.toUpperCase());
        if (command == null) {
            return repository.get(WrongRequest.COMMAND_NAME);
        }
        return command;
    }
}