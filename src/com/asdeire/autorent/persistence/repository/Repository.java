package com.asdeire.autorent.persistence.repository;

import com.asdeire.autorent.persistence.entity.Entity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * A generic repository interface for managing entities.
 *
 * @param <E> the type of entities managed by the repository
 */
public interface Repository<E extends Entity> {

    /**
     * Finds an entity by its unique identifier (ID).
     *
     * @param id the unique identifier of the entity
     * @return an Optional containing the entity if found, or an empty Optional otherwise
     */
    Optional<E> findById(UUID id);

    /**
     * Retrieves all entities stored in the repository.
     *
     * @return a Set containing all entities in the repository
     */
    Set<E> findAll();

    /**
     * Retrieves entities from the repository based on a given filter.
     *
     * @param filter the filter predicate used to select entities
     * @return a Set containing entities that satisfy the given filter
     */
    Set<E> findAll(Predicate<E> filter);

    /**
     * Adds an entity to the repository.
     *
     * @param entity the entity to be added
     * @return the added entity
     */
    E add(E entity);

    /**
     * Removes an entity from the repository.
     *
     * @param entity the entity to be removed
     * @return true if the removal was successful, false otherwise
     */
    boolean remove(E entity);
}
