package com.asdeire.autorent.persistence.entity.impl;


import com.asdeire.autorent.persistence.entity.Entity;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class User extends Entity {

    private final String password;
    private final Role role;
    private String username;
    private String email;
    private int balance;

    public User(UUID id, String password, String email, String username, int balance, Role role) {
        super(id);
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

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

        Role(String name, Map<EntityName, Permission> permissions) {
            this.name = name;
            this.permissions = permissions;
        }

        public enum EntityName {REVIEW, VEHICLE, USER}

        private record Permission(boolean canAdd, boolean canEdit, boolean canDelete,
                                  boolean canRead) {

        }
    }
}
