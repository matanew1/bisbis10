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

    /**
        * Add a new rating
        *
        * @param ratingBoundary The rating boundary
        * @return The response entity ResponseEntity<?> can be any type
        */
    @PostMapping(
            path = "/ratings",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> addNewRating(@RequestBody RatingBoundary ratingBoundary) {
        try {

            // Validate rating boundary
            if (ratingBoundary == null) {
                return ResponseEntity.badRequest().body("Invalid rating: must provide a rating.");
            }

            // Validate rating value and restaurant ID
            if (ratingBoundary.getRating() == null || ratingBoundary.getRestaurantId() == null) {
                return ResponseEntity.badRequest().body("Invalid rating: must provide a rating value and a restaurant ID.");
            }

            // Validate restaurant ID
            int restaurantId = ratingBoundary.getRestaurantId();
            if (restaurantId < 1) {
                return ResponseEntity.badRequest().body("Invalid restaurant ID: must provide a valid restaurant ID.");
            }

            // Validate rating value
            double ratingValue = ratingBoundary.getRating();
            if (ratingValue < 0 || ratingValue > 5) { // Rating must be between 0 and 5
                return ResponseEntity.badRequest().body("Invalid rating value: must be between 0 and 5.");
            }

            // Find the restaurant
            Optional<RestaurantEntity> restaurantEntityOptional = restaurantCrud.findById(restaurantId);
            // Check if the restaurant exists by the provided ID
            if (restaurantEntityOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Get the restaurant entity
            RestaurantEntity restaurantEntity = restaurantEntityOptional.get();

            // Create a new rating entity
            RatingEntity ratingEntity = new RatingEntity();
            // Set the rating entity properties
            ratingEntity.setRestaurant(restaurantEntity);
            // Set the rating value
            ratingEntity.setRating(ratingValue);

            // Save the rating entity
            ratingCrud.save(ratingEntity);

            // Return the response
            return ResponseEntity.ok().body(ratingEntity);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add rating: " + e.getMessage());
        }
    }
}
