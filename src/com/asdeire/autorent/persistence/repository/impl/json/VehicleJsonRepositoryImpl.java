package com.asdeire.autorent.persistence.repository.impl.json;

import com.asdeire.autorent.persistence.entity.impl.Category;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.contracts.VehicleRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the VehicleRepository interface using JSON for persistence.
 */
public class VehicleJsonRepositoryImpl extends GenericJsonRepository<Vehicle> implements VehicleRepository {

    /**
     * Constructs a VehicleJsonRepositoryImpl with the provided Gson instance.
     *
     * @param gson the Gson instance for JSON serialization/deserialization
     */
    public VehicleJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.VEHICLES.getPath(),
            TypeToken.getParameterized(Set.class, Vehicle.class).getType());
    }

    /**
     * Finds all vehicles belonging to a specific category.
     *
     * @param category the category to filter vehicles by
     * @return a Set of vehicles belonging to the specified category
     */
    @Override
    public Set<Vehicle> findAllByCategory(Category category) {
        return entities.stream()
            .filter(vehicle -> vehicle.getCategory().equals(category))
            .collect(Collectors.toSet());
    }
}
