package com.asdeire.autorent.persistence.entity.impl;

public class RentReview {

    private User user;
    private String feedback;

    public RentReview(User user, String feedback) {
        this.user = user;
        this.feedback = feedback;
    }
}
