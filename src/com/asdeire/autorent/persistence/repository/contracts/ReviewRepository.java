package com.asdeire.autorent.persistence.repository.contracts;

import com.asdeire.autorent.persistence.entity.impl.Review;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.repository.Repository;
import java.util.Set;

public interface ReviewRepository extends Repository<Review> {
    Set<Review> findAllByAuthor(User author);
}
