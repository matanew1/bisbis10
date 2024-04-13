package com.att.tdp.bisbis10.bondary;

public class RatingBoundary {
    private Integer restaurantId;
    private Double rating;

    // Getters and setters
    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    // toString
    @Override
    public String toString() {
        return "RatingBoundary{" +
                "restaurantId=" + restaurantId +
                ", rating=" + rating +
                '}';
    }
}
