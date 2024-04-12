package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.bondary.RatingBoundary;
import com.att.tdp.bisbis10.dal.RatingCrud;
import com.att.tdp.bisbis10.dal.RestaurantCrud;
import com.att.tdp.bisbis10.data.RatingEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(path="/api")
public class RatingsController {

    @Autowired
    private RatingCrud ratingCrud;

    @Autowired
    private RestaurantCrud restaurantCrud;

    @PostMapping(
            path = "/ratings",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> addNewRating(@RequestBody RatingBoundary ratingBoundary) {
        try {
            // Validate rating value
            float ratingValue = ratingBoundary.getRating();
            if (ratingValue < 0 || ratingValue > 5) {
                return ResponseEntity.badRequest().body("Invalid rating value: must be between 0 and 5.");
            }

            // Find the restaurant
            Optional<RestaurantEntity> restaurantEntityOptional = restaurantCrud.findById(ratingBoundary.getRestaurantId());
            if (restaurantEntityOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Create RatingEntity from RatingBoundary
            RatingEntity ratingEntity = new RatingEntity();
            ratingEntity.setRating(ratingValue);
            ratingEntity.setRestaurant(restaurantEntityOptional.get());

            // Save the rating
            RatingEntity savedRating = ratingCrud.save(ratingEntity);

            return ResponseEntity.ok(savedRating);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add rating: " + e.getMessage());
        }
    }
}
