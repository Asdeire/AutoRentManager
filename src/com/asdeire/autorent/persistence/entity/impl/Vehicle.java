package com.asdeire.autorent.persistence.entity.impl;

import com.asdeire.autorent.persistence.entity.Entity;
import com.asdeire.autorent.persistence.util.CategoryDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import java.nio.file.Path;

/**
 * Represents a vehicle in the autorent system.
 *
 * <p>
 * This class stores information about vehicles, including their unique identifier,
 * name, price, description, and category.
 * </p>
 */
public class Vehicle extends Entity {

    private Category category;
    private String name;
    private int price;
    private String description;

    /**
     * Constructs a new Vehicle instance.
     *
     * @param id          The unique identifier for the vehicle.
     * @param name        The name of the vehicle.
     * @param price       The price of the vehicle.
     * @param description The description of the vehicle.
     * @param category    The category of the vehicle.
     */
    public Vehicle(UUID id, String name, int price, String description, Category category) {
        super(id);
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    /**
     * Reads a list of vehicles from a JSON file.
     *
     * @param filePath The path to the JSON file.
     * @return The list of vehicles read from the JSON file.
     */
    public static List<Vehicle> readVehiclesFromJsonFile(Path filePath) {
        try (FileReader reader = new FileReader(filePath.toFile())) {
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(Category.class, new CategoryDeserializer())
                .create();

            Type listType = new TypeToken<List<Vehicle>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the name of the vehicle.
     *
     * @return The name of the vehicle.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the vehicle.
     *
     * @return The price of the vehicle.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets the description of the vehicle.
     *
     * @return The description of the vehicle.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the category of the vehicle.
     *
     * @return The category of the vehicle.
     */
    public Category getCategory() {
        return category;
    }
}
