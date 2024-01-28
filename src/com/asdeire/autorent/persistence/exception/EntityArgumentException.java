package com.asdeire.autorent.persistence.exception;

import java.util.List;

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
