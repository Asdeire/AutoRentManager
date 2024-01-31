package com.asdeire.autorent.persistence.entity.impl;

/**
 * The {@code Category} class represents a category for vehicles.
 * It provides information about the category's name.
 * @author Asdeire
 * @version 1.0
 */
public class Category {

    private String name;

    /**
     * Constructs a new category with the specified name.
     *
     * @param name The name of the category.
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the category.
     *
     * @return The name of the category.
     */
    public String getName() {
        return name;
    }

}
