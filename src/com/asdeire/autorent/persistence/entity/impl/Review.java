package com.asdeire.autorent.persistence.entity.impl;

import com.asdeire.autorent.persistence.entity.Entity;
import java.util.UUID;

public class Review extends Entity {

    private User author;
    private String feedback;

    public Review(UUID id, User author, String feedback) {
        super(id);
        this.author = author;
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
