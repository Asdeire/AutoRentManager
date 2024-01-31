package com.asdeire.autorent.domain.exception;

/**
 * The {@code EntityNotFoundException} class is a custom exception thrown to indicate that an entity was not found in the car rental application.
 * It extends the {@link RuntimeException} class and allows providing a custom error message.
 *
 * @author Asdeire
 * @version 1.0
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new instance of {@code EntityNotFoundException} with a custom error message.
     *
     * @param message The custom error message indicating details about the entity not found.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}

