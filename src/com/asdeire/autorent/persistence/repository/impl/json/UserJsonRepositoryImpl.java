package com.asdeire.autorent.persistence.repository.impl.json;

import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.repository.contracts.UserRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Optional;
import java.util.Set;

/**
 * Implementation of the UserRepository interface using JSON for persistence.
 */
public class UserJsonRepositoryImpl extends GenericJsonRepository<User> implements UserRepository {

    /**
     * Constructs a UserJsonRepositoryImpl with the provided Gson instance.
     *
     * @param gson the Gson instance for JSON serialization/deserialization
     */
    public UserJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.USERS.getPath(),
            TypeToken.getParameterized(Set.class, User.class).getType());
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user with the specified username, or empty if not found
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return entities.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the user with the specified email address, or empty if not found
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return entities.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }
}
