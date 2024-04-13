package com.att.tdp.bisbis10.data;

import jakarta.persistence.*;
import java.util.List;

/*
    * The RestaurantEntity class represents the restaurant entity in the database.
    * It is used to map the restaurant table in the database to a Java object.
    * The class uses JPA annotations to define the mapping between the database table and the Java object.
    * The class contains the following fields:
    * - id: The ID of the restaurant.
    * - name: The name of the restaurant.
    * - isKosher: A boolean value indicating whether the restaurant is kosher.
    * - cuisines: A list of cuisines offered by the restaurant.
    * - ratings: A list of ratings given to the restaurant.
    * - dishes: A list of dishes offered by the restaurant.
    * The class also contains the necessary getters and setters for the fields.
 */
@Entity
@Table(name = "restaurants")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_kosher", nullable = false)
    private Boolean isKosher;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "restaurant_cuisines", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "cuisine")
    private List<String> cuisines;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<DishEntity> dishes;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsKosher() {
        return isKosher;
    }

    public void setIsKosher(Boolean isKosher) {
        this.isKosher = isKosher;
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<String> cuisines) {
        this.cuisines = cuisines;
    }

    public List<RatingEntity> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingEntity> ratings) {
        this.ratings = ratings;
    }

    public List<DishEntity> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishEntity> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "RestaurantEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isKosher=" + isKosher +
                ", cuisines=" + cuisines +
                ", ratings=" + ratings +
                ", dishes=" + dishes +
                '}';
    }

}
