package com.asdeire.autorent.persistence.entity.impl;

import com.asdeire.autorent.persistence.entity.Entity;
import java.util.UUID;

/**
 * Represents a user's review for a specific car.
 */
public class Review extends Entity {

    private User user;
    private String feedback;
    private String author;

    /**
     * Constructs a new Review instance.
     *
     * @param id       The unique identifier for the review.
     * @param author   The user who provided the review.
     * @param feedback The feedback content of the review.
     */
    public Review(UUID id, String carName, String author, String feedback) {
        super(id);
        this.author = author;
        this.feedback = feedback;
    }

    /**
     * Gets the feedback content of the review.
     *
     * @return The feedback content.
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Gets the user who provided the review.
     *
     * @return The author (user) of the review.
     */
    public User getAuthor() {
        return user;
    }

    /**
     * Sets the author (user) of the review.
     *
     * @param author The user who provided the review.
     */
    public void setAuthor(User author) {
        this.user = user;
    }
}
