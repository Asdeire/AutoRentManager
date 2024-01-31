package com.asdeire.autorent.domain.exception;

/**
 * The {@code UserAlreadyAuthException} class is a custom exception thrown to indicate that a user is already authenticated in the car rental application.
 * It extends the {@link RuntimeException} class and allows providing a custom error message.
 *
 * @author Asdeire
 * @version 1.0
 */
public class UserAlreadyAuthException extends RuntimeException {

    /**
     * Constructs a new instance of {@code UserAlreadyAuthException} with a custom error message.
     *
     * @param message The custom error message indicating details about the user authentication error.
     */
    public UserAlreadyAuthException(String message) {
        super(message);
    }
}
