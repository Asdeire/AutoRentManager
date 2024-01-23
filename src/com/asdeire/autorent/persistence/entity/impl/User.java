package com.asdeire.autorent.persistence.entity.impl;


import com.asdeire.autorent.persistence.entity.Entity;
import com.asdeire.autorent.persistence.entity.ErrorTemplates;
import com.asdeire.autorent.persistence.exception.EntityArgumentException;
import java.util.UUID;
import java.util.regex.Pattern;

public class User extends Entity {

    private String username;
    private String email;
    private final String password;
    private int balance;

    public User(UUID id, String password, String username, String email, int balance){
        super(id);
        this.email = email;
        this.password = password;
        this.balance = balance;
        setUsername(username);
    }

    public String getUsername(){return username;}

    public int getBalance(){return balance;}

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
}
