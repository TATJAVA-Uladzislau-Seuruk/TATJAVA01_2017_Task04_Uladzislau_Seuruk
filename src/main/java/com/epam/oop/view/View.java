package com.epam.oop.view;

import com.epam.oop.controller.Controller;

/**
 * Implementation of View layer.
 *
 * @author Uladzislau Seuruk.
 */
public class View {
    /**
     * Singleton of this class.
     */
    private static View instance = new View();

    /**
     * Returns instance of this class.
     */
    public static View getInstance() {
        return instance;
    }

    /**
     * Makes request to application.
     *
     * @param request <tt>String</tt> with request.
     */
    public void makeRequest(String request) {
        Controller controller = Controller.getInstance();
        System.out.println(controller.executeCommand(request) + "\n");
    }
}