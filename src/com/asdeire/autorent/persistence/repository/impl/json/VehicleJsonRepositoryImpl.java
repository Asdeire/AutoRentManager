package com.asdeire.autorent.persistence.repository.impl.json;

import com.asdeire.autorent.persistence.entity.impl.Category;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.contracts.VehicleRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Set;
import java.util.stream.Collectors;

public class VehicleJsonRepositoryImpl
        extends  GenericJsonRepository<Vehicle>
        implements VehicleRepository {

    public VehicleJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.VEHICLES.getPath(), TypeToken
            .getParameterized(Set.class, Vehicle.class)
            .getType());
    }

    @Override
    public Set<Vehicle> findAllByCategory(Category category) {
        return entities.stream()
            .filter(c -> c.getCategory().equals(category))
            .collect(Collectors.toSet());
    }
}
