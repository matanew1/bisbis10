package com.att.tdp.bisbis10.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/*
    * This class represents a dish entity in the database.
    * It contains the following fields:
    * - id: the unique identifier of the dish
    * - restaurant: the restaurant that the dish belongs to
    * - name: the name of the dish
    * - description: the description of the dish
    * - price: the price of the dish
    * The class is annotated with JPA annotations to specify the table name and the column names.
    * The class also contains getters and setters for the fields.
    * The class is also annotated with Jackson annotations to ignore the restaurant field during JSON serialization.
    * This is done to break circular references between the restaurant and the dish.
    * The restaurant field is still used in the database, but it is not included in the JSON response.
    * The restaurant field is used to establish a many-to-one relationship between the dish and the restaurant.
    * This means that many dishes can belong to one restaurant.
    * The relationship is established using the @ManyToOne annotation on the restaurant field.
    * The relationship is also specified using the @JoinColumn annotation to specify the foreign key column name.
 */
@Entity
@Table(name = "dishes")
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // Many dishes can belong to one restaurant
    @JoinColumn(name = "restaurant_id", nullable = false) // Foreign key
    @JsonIgnore // Ignore during JSON serialization to break circular references
    private RestaurantEntity restaurant = new RestaurantEntity();

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private double price;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
