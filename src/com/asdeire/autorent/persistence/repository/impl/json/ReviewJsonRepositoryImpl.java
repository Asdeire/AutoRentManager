package com.asdeire.autorent.persistence.repository.impl.json;

import com.asdeire.autorent.persistence.entity.impl.Review;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Set;
import java.util.stream.Collectors;

public class ReviewJsonRepositoryImpl
        extends GenericJsonRepository<Review>
        implements ReviewRepository {

    public ReviewJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.REVIEWS.getPath(), TypeToken
            .getParameterized(Set.class, Review.class)
            .getType());
    }

    @Override
    public Set<Review> findAllByAuthor(User author){
        return entities.stream().filter(l -> l.getAuthor().equals(author))
                .collect(Collectors.toSet());
    }
}
