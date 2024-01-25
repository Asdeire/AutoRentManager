package com.asdeire.autorent.persistence.repository.impl.json;

import java.nio.file.Path;

public enum JsonPathFactory {
    USERS("users.json"),
    VEHICLES("vehicles.json"),
    REVIEWS("reviews.json");

    private static final String DATA_DIRECTORY = "data";
    private final String fileName;

    JsonPathFactory(String fileName) {
        this.fileName = fileName;
    }

    public Path getPath() {
        return Path.of(DATA_DIRECTORY, this.fileName);
    }
}
