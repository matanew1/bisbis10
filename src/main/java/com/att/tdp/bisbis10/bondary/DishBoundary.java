package com.att.tdp.bisbis10.bondary;

import com.att.tdp.bisbis10.data.DishEntity;


public class DishBoundary {
    private String name;
    private String description;
    private Double price;

    // Constructors
    public DishBoundary() {
    }

    public DishBoundary(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // Convert to entity
    public DishEntity toEntity() {
        DishEntity dishEntity = new DishEntity();
        dishEntity.setName(this.name);
        dishEntity.setDescription(this.description);
        dishEntity.setPrice(this.price);
        return dishEntity;
    }
}

