package com.asdeire.autorent.persistence.repository.contracts;

import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.repository.Repository;

import java.util.Optional;

/**
 * Repository interface for managing operations related to users.
 */
public interface UserRepository extends Repository<User> {

    /**
     * Finds a user by username.
     *
     * @param username the username to search for
     * @return an optional containing the user if found, otherwise empty
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by email.
     *
     * @param email the email to search for
     * @return an optional containing the user if found, otherwise empty
     */
    Optional<User> findByEmail(String email);
}
