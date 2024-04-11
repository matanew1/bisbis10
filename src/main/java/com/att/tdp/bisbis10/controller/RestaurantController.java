package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.dal.RestaurantCrud;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping(path="/api")
public class RestaurantController {

    @Autowired
    private RestaurantCrud restaurantCrud;

    @GetMapping("/restaurants")
    public @ResponseBody Iterable<RestaurantEntity> getRestaurantsByCuisine(@RequestParam(required = false) String cuisine) {
        if (cuisine != null && !cuisine.isEmpty()) {
            return restaurantCrud.findByCuisinesContaining(cuisine);
        } else {
            // If cuisine parameter is not provided, return all restaurants
            return restaurantCrud.findAll();
        }
    }

    @GetMapping("/restaurants/{id}")
    public @ResponseBody RestaurantEntity getRestaurantById(@PathVariable Integer id) {
        return restaurantCrud.findById(id).orElse(null);
    }

    @PostMapping(
            path = "/restaurants",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<RestaurantEntity> addRestaurant(@RequestBody RestaurantEntity restaurant) {
        if (restaurant == null) {
            // 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
        RestaurantEntity savedRestaurant = restaurantCrud.save(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);
    }

    @PutMapping(
            path = "/restaurants/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<RestaurantEntity> updateRestaurant(@PathVariable Integer id, @RequestBody RestaurantEntity restaurant) {
        if (restaurant == null) {
            // 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
        RestaurantEntity existingRestaurant = restaurantCrud.findById(id).orElse(null);
        if (existingRestaurant == null) {
            // 404 Not Found
            return ResponseEntity.notFound().build();
        }
        if (restaurant.getName() != null)
            existingRestaurant.setName(restaurant.getName());
        else
            existingRestaurant.setName(existingRestaurant.getName());

        if (restaurant.getIsKosher())
            existingRestaurant.setIsKosher(restaurant.getIsKosher());
        else
            existingRestaurant.setIsKosher(existingRestaurant.getIsKosher());

        if (restaurant.getCuisines() != null)
            existingRestaurant.setCuisines(restaurant.getCuisines());
        else
            existingRestaurant.setCuisines(existingRestaurant.getCuisines());

        RestaurantEntity updatedRestaurant = restaurantCrud.save(existingRestaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable Integer id) {
        if (!restaurantCrud.existsById(id)) {
            // 404 Not Found
            return ResponseEntity.notFound().build();
        }
        restaurantCrud.deleteById(id);
        // 204 No Content
        return ResponseEntity.noContent().build();
    }

}
