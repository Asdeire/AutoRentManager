package com.asdeire.autorent.persistence.repository.contracts;

import com.asdeire.autorent.persistence.entity.impl.Review;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.repository.Repository;

import java.util.Set;

/**
 * Repository interface for managing operations related to reviews.
 */
public interface ReviewRepository extends Repository<Review> {

    /**
     * Finds all reviews written by a specific author.
     *
     * @param author the author of the reviews
     * @return a set of reviews written by the specified author
     */
    Set<Review> findAllByAuthor(User author);
}
