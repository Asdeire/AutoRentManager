package com.asdeire.autorent.domain.exception;

/**
 * The {@code AuthException} class is a custom exception thrown to indicate authentication-related errors in the car rental application.
 * It extends the {@link RuntimeException} class and provides a default message indicating incorrect login credentials.
 *
 * @author [Your Name]
 * @version 1.0
 */
public class AuthException extends RuntimeException {

    /**
     * Constructs a new instance of {@code AuthException} with a default error message.
     */
    public AuthException() {
        super("Не вірний логін чи пароль.");
    }
}
