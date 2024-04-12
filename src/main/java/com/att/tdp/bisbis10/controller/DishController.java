package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.bondary.DishBoundary;
import com.att.tdp.bisbis10.dal.DishCrud;
import com.att.tdp.bisbis10.dal.RestaurantCrud;
import com.att.tdp.bisbis10.data.DishEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/api")
public class DishController {

    @Autowired
    private DishCrud dishCrud;

    @Autowired
    private RestaurantCrud restaurantCrud;

    /**
     * Adds a new dish to the specified restaurant.
     *
     * @param restaurantId   The ID of the restaurant to which the dish will be added.
     * @param dishBoundary   The JSON representation of the dish to be added.
     * @return               ResponseEntity with status 201 (Created) and the created dish entity in the body if successful,
     *                       ResponseEntity with status 404 (Not Found) if the specified restaurant is not found,
     *                       ResponseEntity with status 500 (Internal Server Error) if an unexpected error occurs.
     */
    @PostMapping(
            path = "/restaurants/{id}/dishes",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> addDish(@PathVariable("id") Integer restaurantId, @RequestBody DishBoundary dishBoundary) {
        try {
            // Find the restaurant by ID
            Optional<RestaurantEntity> restaurant = restaurantCrud.findById(restaurantId);

            // If the restaurant is not found, return 404 (Not Found)
            if (restaurant.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Convert the DishBoundary to a DishEntity
            DishEntity dish = dishBoundary.toEntity();

            // Set the restaurant of the dish
            dish.setRestaurant(restaurant.get());

            // Save the dish
            dish = dishCrud.save(dish);

            // Return the created dish
            return ResponseEntity.status(HttpStatus.CREATED).body(dish);
        } catch (Exception e) {
            // Return 500 (Internal Server Error) if an unexpected error occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates the details of a dish in the specified restaurant.
     *
     * @param restaurantId   The ID of the restaurant to which the dish belongs.
     * @param dishId         The ID of the dish to be updated.
     * @param dishBoundary   The JSON representation of the updated dish details.
     * @return               ResponseEntity with status 200 (OK) and the updated dish entity in the body if successful,
     *                       ResponseEntity with status 404 (Not Found) if the specified dish is not found,
     *                       ResponseEntity with status 500 (Internal Server Error) if an unexpected error occurs.
     */
    @PutMapping(
            path = "/restaurants/{restaurantId}/dishes/{dishId}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> updateDish(@PathVariable("restaurantId") Integer restaurantId, @PathVariable("dishId") Integer dishId, @RequestBody DishBoundary dishBoundary) {
        try {
            // Find the dish by ID
            Optional<DishEntity> dish = dishCrud.findById(dishId);

            // If the dish is not found, return 404 (Not Found)
            if (dish.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Update the dish details
            DishEntity dishEntity = dish.get();
            if (dishBoundary.getName() != null)
                dishEntity.setName(dishBoundary.getName());
            if (dishBoundary.getPrice() != null)
                dishEntity.setPrice(dishBoundary.getPrice());
            if (dishBoundary.getDescription() != null)
                dishEntity.setDescription(dishBoundary.getDescription());

            // Save the updated dish
            dishEntity = dishCrud.save(dishEntity);

            // Return the updated dish
            return ResponseEntity.ok(dishEntity);
        } catch (Exception e) {
            // Return 500 (Internal Server Error) if an unexpected error occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Deletes a dish from the specified restaurant.
     *
     * @param restaurantId   The ID of the restaurant from which the dish will be deleted.
     * @param dishId         The ID of the dish to be deleted.
     * @return               ResponseEntity with status 204 (No Content) if the dish is successfully deleted,
     *                       ResponseEntity with status 404 (Not Found) if the specified dish is not found,
     *                       ResponseEntity with status 500 (Internal Server Error) if an unexpected error occurs.
     */
    @DeleteMapping(
            path = "/restaurants/{restaurantId}/dishes/{dishId}"
    )
    public ResponseEntity<Void> deleteDish(@PathVariable("restaurantId") Integer restaurantId, @PathVariable("dishId") Integer dishId) {
        try {
            // Find the dish by ID
            Optional<DishEntity> dish = dishCrud.findById(dishId);

            // If the dish is not found, return 404 (Not Found)
            if (dish.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Delete the dish
            dishCrud.delete(dish.get());

            // Return 204 (No Content) if the dish is successfully deleted
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Return 500 (Internal Server Error) if an unexpected error occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves all dishes belonging to the specified restaurant.
     *
     * @param restaurantId   The ID of the restaurant for which dishes will be retrieved.
     * @return               ResponseEntity with status 200 (OK) and the list of dishes in the body if successful,
     *                       ResponseEntity with status 404 (Not Found) if the specified restaurant is not found,
     *                       ResponseEntity with status 500 (Internal Server Error) if an unexpected error occurs.
     */
    @GetMapping(
            path = "/restaurants/{id}/dishes",
            produces = "application/json"
    )
    public ResponseEntity<?> getDishesByRestaurantId(@PathVariable("id") Integer restaurantId) {
        try {
            // Find the restaurant by ID
            Optional<RestaurantEntity> restaurant = restaurantCrud.findById(restaurantId);

            // If the restaurant is not found, return 404 (Not Found)
            return restaurant.map(value -> ResponseEntity.ok(value.getDishes()))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Return 500 (Internal Server Error) if an unexpected error occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
