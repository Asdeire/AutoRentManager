package com.asdeire.autorent.persistence.entity.impl;


import com.asdeire.autorent.persistence.entity.Entity;
import com.asdeire.autorent.persistence.entity.ErrorTemplates;
import com.asdeire.autorent.persistence.exception.EntityArgumentException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class User extends Entity {

    private String username;
    private String email;
    private final String password;
    private int balance;
    private final Role role;

    public User(UUID id, String password, String username, String email, int balance, Role role){
        super(id);
        this.email = email;
        this.password = validatedPassword(password);
        this.balance = balance;
        setUsername(username);
        this.role = role;
    }

    public String getUsername(){return username;}

    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}

    public int getBalance(){return balance;}

    public void setBalance(int balance){this.balance = balance;}

    public void setUsername(String username) {
        final String templateName = "логіну";

        if (username.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (username.length() < 4) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (username.length() > 24) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 24));
        }
        var pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        if (pattern.matcher(username).matches()) {
            errors.add(ErrorTemplates.ONLY_LATIN.getTemplate().formatted(templateName, 24));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        this.username = username;
    }

    private String validatedPassword(String password) {
        final String templateName = "паролю";

        if (password.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (password.length() < 8) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (password.length() > 32) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 32));
        }
        var pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$");
        if (pattern.matcher(password).matches()) {
            errors.add(ErrorTemplates.PASSWORD.getTemplate().formatted(templateName, 24));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return password;
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
