package com.att.tdp.bisbis10.controller;


import com.att.tdp.bisbis10.dal.RatingCrud;
import com.att.tdp.bisbis10.dal.RestaurantCrud;
import com.att.tdp.bisbis10.data.RatingBoundary;
import com.att.tdp.bisbis10.data.RatingEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        int restaurantId = ratingBoundary.getRestaurantId();
        float ratingValue = ratingBoundary.getRating();

        Optional<RestaurantEntity> restaurantEntityOptional = restaurantCrud.findById(restaurantId);
        if (restaurantEntityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        restaurantEntityOptional.get().getRating().setRating(ratingValue);
        restaurantCrud.save(restaurantEntityOptional.get());

        return ResponseEntity.ok(restaurantEntityOptional.get().getRating());

    }


}

