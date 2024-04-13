package com.att.tdp.bisbis10.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/*
    * The RatingEntity class represents the rating of a restaurant.
    * It contains the following properties:
    * id: The ID of the rating.
    * restaurant: The restaurant entity.
    * rating: The rating of the restaurant.
    * The class also contains the getters and setters for the properties.
    * The class is annotated with the following:
    * @Entity: Specifies that the class is an entity.
    * @Table: Specifies the name of the table to be used for mapping.
    * @Id: Specifies the primary key of the entity.
    * @GeneratedValue: Specifies the generation strategy for the primary key.
    * @ManyToOne: Specifies the many-to-one relationship between the rating and the restaurant.
    * @JoinColumn: Specifies the column to be used for joining the entities.
    * @Column: Specifies the column to be used for mapping.
    * @JsonIgnore: Specifies that the property should be ignored during serialization and deserialization.
    * The RatingEntity class is used to store the rating of a restaurant in the database.
    * The rating is associated with a restaurant entity using a many-to-one relationship.
    * The class is used to map the rating data to the database table "restaurant_ratings".
    * The class is used to store and retrieve the rating data from the database.
    * The class is used to represent the rating of a restaurant in the application.
 */
@Entity
@Table(name = "restaurant_ratings")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore // Prevent infinite recursion
    private RestaurantEntity restaurant = new RestaurantEntity();

    @Column(nullable = false)
    private Double rating;

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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
