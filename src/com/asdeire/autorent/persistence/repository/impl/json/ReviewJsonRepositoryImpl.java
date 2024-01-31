package com.asdeire.autorent.persistence.repository.impl.json;

import com.asdeire.autorent.persistence.entity.impl.Review;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the ReviewRepository interface using JSON for persistence.
 */
public class ReviewJsonRepositoryImpl extends GenericJsonRepository<Review> implements ReviewRepository {

    /**
     * Constructs a ReviewJsonRepositoryImpl with the provided Gson instance.
     *
     * @param gson the Gson instance for JSON serialization/deserialization
     */
    public ReviewJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.REVIEWS.getPath(),
            TypeToken.getParameterized(Set.class, Review.class).getType());
    }

    /**
     * Retrieves all reviews associated with a specific author.
     *
     * @param author the author for whom reviews are to be retrieved
     * @return a set of reviews written by the specified author
     */
    @Override
    public Set<Review> findAllByAuthor(User author) {
        return entities.stream().filter(review -> review.getAuthor().equals(author))
            .collect(Collectors.toSet());
    }
}
