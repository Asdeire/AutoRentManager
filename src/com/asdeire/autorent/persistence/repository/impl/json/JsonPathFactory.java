package com.asdeire.autorent.persistence.repository.impl.json;

import java.nio.file.Path;

/**
 * Enum for generating paths to JSON files related to user data, vehicles, and reviews.
 */
public enum JsonPathFactory {
    USERS("users.json"),
    VEHICLES("vehicles.json"),
    REVIEWS("reviews.json");

    private static final String DATA_DIRECTORY = "data";
    private final String fileName;

    /**
     * Constructs a JsonPathFactory enum constant with the specified file name.
     *
     * @param fileName the name of the JSON file
     */
    JsonPathFactory(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the path to the corresponding JSON file.
     *
     * @return the path to the JSON file
     */
    public Path getPath() {
        return Path.of(DATA_DIRECTORY, this.fileName);
    }
}
