package com.asdeire.autorent.domain.impl;

import com.asdeire.autorent.persistence.entity.impl.Review;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import java.util.UUID;

public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void leaveReview(User author, String feedback) {
        UUID reviewId = UUID.randomUUID();
        Review review = new Review(reviewId, author, feedback);
        reviewRepository.add(review);
    }
}
