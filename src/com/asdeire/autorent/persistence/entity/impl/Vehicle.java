package com.asdeire.autorent.persistence.entity.impl;

public class Vehicle {

    private Category category;
    private String name;
    private int price;
    private String description;

    public Vehicle(Category category, String name, int price, String description){
        this.category = category;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName(){return name;}

    public int getPrice(){return price;}

    public String getDescription(){return description;}
}
