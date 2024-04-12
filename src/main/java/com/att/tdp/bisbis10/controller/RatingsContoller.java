package com.att.tdp.bisbis10.controller;


import com.att.tdp.bisbis10.dal.RatingCrud;
import com.att.tdp.bisbis10.dal.RestaurantCrud;
import com.att.tdp.bisbis10.bondary.RatingBoundary;
import com.att.tdp.bisbis10.data.RatingEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/api")
public class RatingsContoller {

    @Autowired
    private RatingCrud ratingCrud;

    @Autowired
    private RestaurantCrud restaurantCrud;
    @Autowired
    private ServerProperties serverProperties;


    @PostMapping(
            path = "/ratings",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<RatingEntity> addNewRating(@RequestBody RatingBoundary ratingBoundary) {
        // Convert RatingBoundary to RatingEntity
        RatingEntity ratingEntity = ratingBoundary.toEntity();
        System.err.println("ratingEntity: " + ratingEntity);

        // Find the restaurant
        Optional<RestaurantEntity> restaurantEntity = restaurantCrud.findById(ratingBoundary.getRestaurantId());
        if (restaurantEntity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Set the restaurant
        ratingEntity.setRestaurant(restaurantEntity.get());

        // Return all ratings
        return ResponseEntity.ok(ratingCrud.save(ratingEntity));

    }


}

