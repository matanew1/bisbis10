package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.dal.RestaurantCrud;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api")
public class RestaurantController {

    @Autowired
    private RestaurantCrud restaurantCrud;

    @GetMapping("/restaurants")
    public ResponseEntity<?> getRestaurantsByCuisine(@RequestParam(required = false) String cuisine) {
        try {
            if (cuisine != null && !cuisine.isEmpty()) {
                return ResponseEntity.ok(restaurantCrud.findByCuisinesContaining(cuisine));
            } else {
                return ResponseEntity.ok(restaurantCrud.findAll());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable Integer id) {
        try {
            RestaurantEntity restaurant = restaurantCrud.findById(id).orElse(null);
            if (restaurant == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(restaurant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(
            path = "/restaurants",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> addRestaurant(@RequestBody RestaurantEntity restaurant) {
        try {
            if (restaurant == null) {
                return ResponseEntity.badRequest().build();
            }
            RestaurantEntity savedRestaurant = restaurantCrud.save(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(
            path = "/restaurants/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> updateRestaurant(@PathVariable Integer id, @RequestBody RestaurantEntity restaurant) {
        try {
            if (restaurant == null) {
                return ResponseEntity.badRequest().build();
            }
            RestaurantEntity existingRestaurant = restaurantCrud.findById(id).orElse(null);
            if (existingRestaurant == null) {
                return ResponseEntity.notFound().build();
            }
            if (restaurant.getName() != null)
                existingRestaurant.setName(restaurant.getName());
            if (restaurant.getIsKosher() != null)
                existingRestaurant.setIsKosher(restaurant.getIsKosher());
            if (restaurant.getCuisines() != null)
                existingRestaurant.setCuisines(restaurant.getCuisines());

            RestaurantEntity updatedRestaurant = restaurantCrud.save(existingRestaurant);
            return ResponseEntity.ok(updatedRestaurant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Integer id) {
        try {
            if (!restaurantCrud.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            restaurantCrud.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
