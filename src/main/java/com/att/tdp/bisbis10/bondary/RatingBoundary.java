package com.att.tdp.bisbis10.bondary;

import com.att.tdp.bisbis10.data.RatingEntity;

public class RatingBoundary {
    private int restaurantId;
    private float rating;

    // Getters and setters
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public RatingEntity toEntity() {
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.getRestaurant().setId(restaurantId);
        ratingEntity.setRating(rating);
        return ratingEntity;
    }
}
