package com.asdeire.autorent.persistence.exception;

/**
 * Exception thrown when an entity is not found in the persistence layer.
 *
 * <p>
 * This exception is typically used to indicate that a requested entity could not be
 * found in the data store.
 * </p>
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new EntityNotFoundException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the
     *                getMessage() method).
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
