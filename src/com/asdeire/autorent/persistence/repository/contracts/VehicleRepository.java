package com.asdeire.autorent.persistence.repository.contracts;

import com.asdeire.autorent.persistence.entity.impl.Category;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.Repository;

import java.util.Set;

/**
 * Repository interface for managing operations related to vehicles.
 */
public interface VehicleRepository extends Repository<Vehicle> {

    /**
     * Finds all vehicles belonging to the specified category.
     *
     * @param category the category to filter vehicles by
     * @return a set of vehicles within the specified category
     */
    Set<Vehicle> findAllByCategory(Category category);
}
