package com.asdeire.autorent.appui;

import java.io.IOException;

/**
 * The {@code Renderable} interface defines a contract for classes that can be rendered in the user interface.
 * Classes implementing this interface should provide an implementation for the {@code render} method.
 *
 * @author Asdeire
 * @version 1.0
 */
public interface Rendarable {

    /**
     * Renders the user interface for the implementing class.
     *
     * @throws IOException If an I/O error occurs during rendering.
     */
    void render() throws IOException;
}
