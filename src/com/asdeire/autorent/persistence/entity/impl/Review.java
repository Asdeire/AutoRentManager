package com.asdeire.autorent.persistence.entity.impl;

import com.asdeire.autorent.persistence.entity.Entity;
import java.util.UUID;

public class Review extends Entity {

    private User user;
    private String feedback;
    private String author;

    public Review(UUID id, String carName, String author, String feedback) {
        super(id);
        this.author = author;
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public User getAuthor() {
        return user;
    }

    public void setAuthor(User author) {
        this.user = user;
    }
}
