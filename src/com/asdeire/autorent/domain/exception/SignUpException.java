package com.asdeire.autorent.domain.exception;

/**
 * The {@code SignUpException} class is a custom exception thrown to indicate errors related to user sign-up in the car rental application.
 * It extends the {@link RuntimeException} class and allows providing a custom error message.
 *
 * @author Asdeire
 * @version 1.0
 */
public class SignUpException extends RuntimeException {

    /**
     * Constructs a new instance of {@code SignUpException} with a custom error message.
     *
     * @param message The custom error message indicating details about the sign-up error.
     */
    public SignUpException(String message) {
        super(message);
    }
}

