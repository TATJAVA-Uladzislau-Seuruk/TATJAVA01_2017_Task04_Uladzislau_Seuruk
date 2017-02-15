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

    private View() {}

    /**
     * Closes program.
     */
    public void finish() {
        Controller.getInstance().finish();
        System.out.println("Good bye!");
    }

    /**
     * Makes request to application.
     *
     * @param request <tt>String</tt> with request.
     */
    public void makeRequest(String request) {
        Controller controller = Controller.getInstance();
        String response = controller.executeCommand(request);
        System.out.println(response + "\n");
    }

    /**
     * Starts program.
     */
    public void start() {
        String response = Controller.getInstance().start();
        System.out.println(response + "\n");
    }
}