package com.asdeire.autorent.persistence.exception;

import java.util.List;

/**
 * Exception thrown when an entity encounters invalid arguments during validation.
 *
 * <p>
 * This exception is typically used to indicate that an entity has failed validation due
 * to invalid arguments or constraints. It holds a list of error messages describing the
 * validation issues with the entity fields.
 * </p>
 */
public class EntityArgumentException extends IllegalArgumentException {

    /**
     * List of error messages describing the validation issues with the entity fields.
     */
    private final List<String> errors;

    /**
     * Constructs a new {@code EntityArgumentException} with the specified list of error messages.
     *
     * @param errors the list of error messages indicating validation issues with entity fields
     */
    public EntityArgumentException(List<String> errors) {
        this.errors = errors;
    }

    /**
     * Retrieves the list of error messages associated with this exception.
     *
     * @return the list of error messages
     */
    public List<String> getErrors() {
        return errors;
    }
}
