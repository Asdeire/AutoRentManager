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

public class Vehicle extends Entity {

    private Category category;
    private String name;
    private int price;
    private String description;

    public Vehicle(UUID id, String name, int price, String description, Category category) {
        super(id);
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static List<Vehicle> readVehiclesFromJsonFile(Path filePath) {
        try (FileReader reader = new FileReader(filePath.toFile())) {
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(Category.class, new CategoryDeserializer())
                .create();

            Type listType = new TypeToken<List<Vehicle>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
