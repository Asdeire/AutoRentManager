package com.asdeire.autorent.persistence.repository;

import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import com.asdeire.autorent.persistence.repository.contracts.UserRepository;
import com.asdeire.autorent.persistence.repository.impl.json.JsonRepositoryFactory;
import org.apache.commons.lang3.NotImplementedException;

/**
 * An abstract factory class responsible for creating repository instances.
 */
public abstract class RepositoryFactory {

    public static final int JSON = 1;
    public static final int XML = 2;
    public static final int POSTGRESQL = 3;

    /**
     * Retrieves a specific repository factory based on the provided factory type.
     *
     * @param whichFactory the type of repository factory to retrieve
     * @return a repository factory instance
     */
    public static RepositoryFactory getRepositoryFactory(int whichFactory) {
        return switch (whichFactory) {
            case JSON -> JsonRepositoryFactory.getInstance();
            case XML -> throw new NotImplementedException("Робота з XML файлами не реалізована.");
            case POSTGRESQL -> throw new NotImplementedException(
                "Робота з СУБД PostgreSQL не реалізована.");
            default -> throw new IllegalArgumentException(
                "Помилка при виборі фабрики репозиторіїв.");
        };
    }

    /**
     * Retrieves the review repository instance.
     *
     * @return the review repository
     */
    public abstract ReviewRepository getReviewRepository();

    /**
     * Retrieves the user repository instance.
     *
     * @return the user repository
     */
    public abstract UserRepository getUserRepository();

    /**
     * Commits any changes made in the repositories.
     */
    public abstract void commit();
}
