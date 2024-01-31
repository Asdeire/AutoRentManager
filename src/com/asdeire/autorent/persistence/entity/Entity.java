package com.asdeire.autorent.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a generic entity in the autorent system.
 *
 * <p>
 * This abstract class serves as the base class for various entities in the system,
 * providing a unique identifier, error handling functionality, and equality methods.
 * </p>
 */
public abstract class Entity {

    /**
     * The unique identifier for the entity.
     */
    protected final UUID id;

    /**
     * List to store validation errors.
     */
    protected transient List<String> errors;

    /**
     * Flag indicating whether the entity is valid.
     */
    protected transient boolean isValid;

    /**
     * Constructs a new Entity instance.
     *
     * @param id The unique identifier for the entity.
     */
    protected Entity(UUID id) {
        errors = new ArrayList<>();
        this.id = id;
    }

    /**
     * Gets the unique identifier of the entity.
     *
     * @return The unique identifier.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Checks if the entity is valid.
     *
     * @return True if the entity is valid, false otherwise.
     */
    public boolean isValid() {
        return errors.isEmpty();
    }

    /**
     * Gets the list of validation errors.
     *
     * @return The list of validation errors.
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Checks if two entities are equal based on their unique identifiers.
     *
     * @param o The object to compare with.
     * @return True if the entities are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id);
    }

    /**
     * Generates the hash code based on the unique identifier.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
