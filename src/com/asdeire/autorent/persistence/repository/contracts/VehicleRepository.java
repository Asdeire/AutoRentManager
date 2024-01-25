package com.asdeire.autorent.persistence.repository.contracts;

import com.asdeire.autorent.persistence.entity.impl.Category;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.Repository;
import java.util.Set;

public interface VehicleRepository extends Repository<Vehicle> {

    Set<Vehicle> findAllByCategory(Category category);
}
