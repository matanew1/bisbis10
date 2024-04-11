package com.att.tdp.bisbis10.data;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "is_kosher")
    private boolean isKosher;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "restaurant_cuisines", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "cuisine")
    private List<String> cuisines;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "rating_id")
    private RatingEntity rating;

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

    public RatingEntity getRating() {
        return rating;
    }

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "RestaurantEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isKosher=" + isKosher +
                ", cuisines=" + cuisines +
                ", rating=" + rating +
                '}';
    }

}
