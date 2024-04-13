package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.bondary.RestaurantBoundary;
import com.att.tdp.bisbis10.dal.RestaurantCrud;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/api")
public class RestaurantController {

    @Autowired
    private RestaurantCrud restaurantCrud;

    /**
     * Get all restaurants or by cuisine
     * @param cuisine - optional
     * @return List of restaurants or empty list
     */
    @GetMapping("/restaurants")
    public ResponseEntity<?> getRestaurantsByCuisine(@RequestParam(required = false) String cuisine) {
        try {
            // If cuisine is provided, return restaurants by cuisine
            if (cuisine != null && !cuisine.isEmpty()) {
                return ResponseEntity.ok(restaurantCrud.findByCuisinesContaining(cuisine.toLowerCase()));
            } else { // If cuisine is not provided, return all restaurants
                return ResponseEntity.ok(restaurantCrud.findAll());
            }
        } catch (Exception e) {
            // Return 500 Internal Server Error if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get restaurant by ID
     * @param id - restaurant ID
     * @return Restaurant or 404 Not Found
     */
    @GetMapping("/restaurants/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable Integer id) {
        try {
            // If ID is invalid, return 400 Bad Request
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body("Invalid ID");
            }

            // If restaurant is not found, return 404 Not Found
            RestaurantEntity restaurant = restaurantCrud.findById(id).orElse(null);
            if (restaurant == null) {
                return ResponseEntity.notFound().build();
            }

            // Return 200 OK with the restaurant
            return ResponseEntity.ok(restaurant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Add a new restaurant
     * @param restaurant - restaurant to add
     * @return Created restaurant or 500 Internal Server Error
     */
    @PostMapping(
            path = "/restaurants",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> addRestaurant(@RequestBody RestaurantBoundary restaurant) {
        try {
            // If restaurant is null, return 400 Bad Request
            if (restaurant == null ) {
                return ResponseEntity.badRequest().body("Invalid restaurant");
            }
            // If restaurant name is null, return 400 Bad Request
            if (restaurant.getName() == null || restaurant.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid restaurant name");
            }

            // If restaurant cuisines is null, return 400 Bad Request
            if (restaurant.getCuisines() == null || restaurant.getCuisines().isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid restaurant cuisines");
            }

            // If restaurant isKosher is null, return 400 Bad Request
            if (restaurant.getIsKosher() == null) {
                return ResponseEntity.badRequest().body("Invalid restaurant isKosher");
            }

            RestaurantEntity restaurantEntity = restaurant.toEntity();

            // Save the new restaurant
            RestaurantEntity newRestaurant = restaurantCrud.save(restaurantEntity);

            // Return 200 OK with the new restaurant
            return ResponseEntity.ok(newRestaurant);
        } catch (Exception e) {
            // Return 500 Internal Server Error if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Update a restaurant
     * @param id - restaurant ID
     * @param restaurant - restaurant to update
     * @return Updated restaurant or 404 Not Found
     */
    @PutMapping(
            path = "/restaurants/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> updateRestaurant(@PathVariable Integer id, @RequestBody RestaurantBoundary restaurant) {
        try {

            // If restaurant is null, return 400 Bad Request
            if (restaurant == null) {
                return ResponseEntity.badRequest().body("Invalid restaurant");
            }

            // If ID is invalid, return 400 Bad Request
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body("Invalid ID");
            }

            // If restaurant name is null, return 404 not found
            Optional<RestaurantEntity> restaurantOptional = restaurantCrud.findById(id);
            if (restaurantOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            RestaurantEntity restaurantEntity = restaurantOptional.get();

            // Update the restaurant
            if (restaurant.getName() != null && !restaurant.getName().isEmpty()) {
                restaurantEntity.setName(restaurant.getName());
            }

            // Update the restaurant cuisines
            if (restaurant.getCuisines() != null && !restaurant.getCuisines().isEmpty()) {
                restaurantEntity.setCuisines(restaurant.getCuisines());
                restaurantEntity.getCuisines().replaceAll(String::toLowerCase);
            }

            // Update the restaurant isKosher
            if (restaurant.getIsKosher() != null) {
                restaurantEntity.setIsKosher(restaurant.getIsKosher());
            }

            // Save the updated restaurant
            RestaurantEntity updatedRestaurant = restaurantCrud.save(restaurantEntity);

            // Return 200 OK with the updated restaurant
            return ResponseEntity.ok(updatedRestaurant);


        } catch (Exception e) {
            // Return 500 Internal Server Error if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Delete a restaurant
     * @param id - restaurant ID
     * @return 204 No Content or 404 Not Found
     */
    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Integer id) {
        try {
            // If ID is invalid, return 400 Bad Request
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body("Invalid ID");
            }
            // If restaurant is not found, return 404 Not Found
            if (!restaurantCrud.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            // Delete the restaurant
            restaurantCrud.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Return 500 Internal Server Error if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
