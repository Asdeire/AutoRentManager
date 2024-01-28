package com.asdeire.autorent.persistence.repository.contracts;

import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.repository.Repository;
import java.util.Optional;

public interface UserRepository extends Repository<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
