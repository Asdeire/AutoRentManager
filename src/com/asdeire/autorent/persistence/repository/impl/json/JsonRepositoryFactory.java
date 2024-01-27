package com.asdeire.autorent.persistence.repository.impl.json;

import com.asdeire.autorent.persistence.entity.Entity;
import com.asdeire.autorent.persistence.exception.JsonFileIOException;
import com.asdeire.autorent.persistence.repository.RepositoryFactory;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import com.asdeire.autorent.persistence.repository.contracts.UserRepository;
import com.asdeire.autorent.persistence.repository.contracts.VehicleRepository;
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

public class JsonRepositoryFactory extends RepositoryFactory {

    private final Gson gson;
    private VehicleJsonRepositoryImpl vehicleJsonRepositoryImpl;
    private ReviewJsonRepositoryImpl reviewJsonRepositoryImpl;
    private UserJsonRepositoryImpl userJsonRepositoryImpl;

    private JsonRepositoryFactory() {
        // Адаптер для типу даних LocalDateTime при серіалізації/десеріалізації
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

        // Адаптер для типу даних LocalDate при серіалізації/десеріалізації
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

    public static JsonRepositoryFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public VehicleRepository getVehicleRepository() {
        return vehicleJsonRepositoryImpl;
    }

    @Override
    public ReviewRepository getReviewRepository() {
        return reviewJsonRepositoryImpl;
    }

    @Override
    public UserRepository getUserRepository() {
        return userJsonRepositoryImpl;
    }

    public void commit() {
        serializeEntities(reviewJsonRepositoryImpl.getPath(), reviewJsonRepositoryImpl.findAll());
        serializeEntities(userJsonRepositoryImpl.getPath(), userJsonRepositoryImpl.findAll());
    }

    private <E extends Entity> void serializeEntities(Path path, Set<E> entities) {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            // Скидуємо файлик, перед збереженням!
            writer.write("");
            // Перетворюємо колекцію користувачів в JSON та записуємо у файл
            gson.toJson(entities, writer);

        } catch (IOException e) {
            throw new JsonFileIOException("Не вдалось зберегти дані у json-файл. Детальніше: %s"
                .formatted(e.getMessage()));
        }
    }


    private static class InstanceHolder {

        public static final JsonRepositoryFactory INSTANCE = new JsonRepositoryFactory();
    }
}
