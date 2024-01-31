package com.asdeire.autorent.persistence.entity.impl;


import com.asdeire.autorent.persistence.entity.Entity;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a user in the autorent system.
 *
 * <p>
 * This class stores information about users, including their unique identifier,
 * password, email, username, balance, and role in the autorent system.
 * </p>
 */
public class User extends Entity {

    private final String password;
    private final Role role;
    private String username;
    private String email;
    private int balance;

    /**
     * Constructs a new User instance.
     *
     * @param id       The unique identifier for the user.
     * @param password The user's password.
     * @param email    The user's email address.
     * @param username The username chosen by the user.
     * @param balance  The user's balance.
     * @param role     The role of the user (ADMIN or GENERAL).
     */
    public User(UUID id, String password, String email, String username, int balance, Role role) {
        super(id);
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.username = username;
        this.role = role;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the email of the user.
     *
     * @return The email.
     */
    public String getEmail() {
        return username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the balance of the user.
     *
     * @return The balance.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the user.
     *
     * @param balance The new balance.
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Checks if two users are equal based on their email addresses.
     *
     * @param o The object to compare.
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    /**
     * Generates a hash code for the user based on their email address.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    /**
     * Represents the role of a user in the autorent system.
     */
    public enum Role {
        ADMIN("admin", Map.of(
            EntityName.VEHICLE, new Permission(true, true, true, true),
            EntityName.REVIEW, new Permission(true, true, true, true),
            EntityName.USER, new Permission(true, true, true, true))),
        GENERAL("general", Map.of(
            EntityName.VEHICLE, new Permission(true, true, true, true),
            EntityName.REVIEW, new Permission(true, false, true, true),
            EntityName.USER, new Permission(false, false, false, false)));

        private final String name;
        private final Map<EntityName, Permission> permissions;

        /**
         * Constructs a new Role instance.
         *
         * @param name        The name of the role.
         * @param permissions The permissions associated with the role.
         */
        Role(String name, Map<EntityName, Permission> permissions) {
            this.name = name;
            this.permissions = permissions;
        }

        /**
         * Represents the entity names for which permissions are defined.
         */
        public enum EntityName {REVIEW, VEHICLE, USER}

        /**
         * Represents the permissions associated with a role for a specific entity.
         */
        private record Permission(boolean canAdd, boolean canEdit, boolean canDelete,
                                  boolean canRead) {

        }
    }
}
