package com.asdeire.autorent.persistence.entity.impl;

import com.asdeire.autorent.persistence.entity.Entity;
import java.util.UUID;

public class Vehicle extends Entity {

    private Category category;
    private String name;
    private int price;
    private String description;

    public Vehicle(UUID id, String name, int price, String description, Category category){
        super(id);
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public String getName(){return name;}

    public int getPrice(){return price;}

    public String getDescription(){return description;}

    public Category getCategory(){return category;}

    public void setCategory(Category category){this.category = category;}
}
