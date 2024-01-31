package com.asdeire.autorent.persistence.repository.impl.json;

import com.asdeire.autorent.persistence.entity.Entity;
import com.asdeire.autorent.persistence.exception.JsonFileIOException;
import com.asdeire.autorent.persistence.repository.RepositoryFactory;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import com.asdeire.autorent.persistence.repository.contracts.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

/**
 * Factory for creating JSON repositories and serializing entities to JSON files.
 */
public class JsonRepositoryFactory extends RepositoryFactory {

    private final Gson gson;
    private final ReviewJsonRepositoryImpl reviewJsonRepositoryImpl;
    private final UserJsonRepositoryImpl userJsonRepositoryImpl;

    private JsonRepositoryFactory() {
        // Adapter for LocalDateTime data type during serialization/deserialization
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class,
            (JsonSerializer<LocalDateTime>) (localDate, srcType, context) ->
                new JsonPrimitive(
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(localDate)));
        gsonBuilder.registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                LocalDateTime.parse(json.getAsString(),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                        .withLocale(Locale.of("uk", "UA"))));

        // Adapter for LocalDate data type during serialization/deserialization
        gsonBuilder.registerTypeAdapter(LocalDate.class,
            (JsonSerializer<LocalDate>) (localDate, srcType, context) ->
                new JsonPrimitive(
                    DateTimeFormatter.ofPattern("dd-MM-yyyy").format(localDate)));
        gsonBuilder.registerTypeAdapter(LocalDate.class,
            (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                LocalDate.parse(json.getAsString(),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy")
                        .withLocale(Locale.of("uk", "UA"))));

        gson = gsonBuilder.setPrettyPrinting().create();

        reviewJsonRepositoryImpl = new ReviewJsonRepositoryImpl(gson);
        userJsonRepositoryImpl = new UserJsonRepositoryImpl(gson);
    }

    /**
     * Gets the singleton instance of the JsonRepositoryFactory.
     *
     * @return the JsonRepositoryFactory instance
     */
    public static JsonRepositoryFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public ReviewRepository getReviewRepository() {
        return reviewJsonRepositoryImpl;
    }

    @Override
    public UserRepository getUserRepository() {
        return userJsonRepositoryImpl;
    }

    /**
     * Commits changes by serializing entities to JSON files.
     */
    public void commit() {
        serializeEntities(reviewJsonRepositoryImpl.getPath(), reviewJsonRepositoryImpl.findAll());
        serializeEntities(userJsonRepositoryImpl.getPath(), userJsonRepositoryImpl.findAll());
    }

    /**
     * Serializes entities to a JSON file.
     *
     * @param path     the path to the JSON file
     * @param entities the set of entities to be serialized
     * @param <E>      the type of entities
     */
    private <E extends Entity> void serializeEntities(Path path, Set<E> entities) {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            // Clear the file before saving!
            writer.write("");
            // Convert the collection of entities to JSON and write to the file
            gson.toJson(entities, writer);

        } catch (IOException e) {
            throw new JsonFileIOException("Failed to save data to the json file. Details: %s"
                .formatted(e.getMessage()));
        }
    }

    private static class InstanceHolder {
        public static final JsonRepositoryFactory INSTANCE = new JsonRepositoryFactory();
    }
}
