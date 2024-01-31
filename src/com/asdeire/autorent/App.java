package com.asdeire.autorent;

/**
 * The {@code App} class serves as the entry point for the Autorent application.
 * It initializes the application by calling the {@link Startup#init()} method.
 */
public class App {

    /**
     * The main method of the Autorent application.
     * It serves as the entry point and calls the initialization method {@link Startup#init()}.
     *
     * @param args The command-line arguments passed to the application (not used in this case).
     */
    public static void main(String[] args) {
        Startup.init();
    }
}
