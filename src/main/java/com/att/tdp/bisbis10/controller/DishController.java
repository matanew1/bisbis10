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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/api")
public class DishController {

    @Autowired
    private DishCrud dishCrud;

    @Autowired
    private RestaurantCrud restaurantCrud;

    @PostMapping(
            path = "/restaurants/{id}/dishes",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> addDish(@PathVariable("id") Integer restaurantId, @RequestBody DishBoundary dishBoundary) {
        try {
            Optional<RestaurantEntity> restaurant = restaurantCrud.findById(restaurantId);

            if (restaurant.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            DishEntity dish = dishBoundary.toEntity();
            dish.setRestaurant(restaurant.get());

            dish = dishCrud.save(dish);

            return ResponseEntity.status(HttpStatus.CREATED).body(dish);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(
            path = "/restaurants/{restaurantId}/dishes/{dishId}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> updateDish(@PathVariable("restaurantId") Integer restaurantId, @PathVariable("dishId") Integer dishId, @RequestBody DishBoundary dishBoundary) {
        try {
            Optional<DishEntity> dish = dishCrud.findById(dishId);

            if (dish.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            DishEntity dishEntity = dish.get();
            if (dishBoundary.getName() != null)
                dishEntity.setName(dishBoundary.getName());
            if (dishBoundary.getPrice() != null)
                dishEntity.setPrice(dishBoundary.getPrice());
            if (dishBoundary.getDescription() != null)
                dishEntity.setDescription(dishBoundary.getDescription());

            dishEntity = dishCrud.save(dishEntity);

            return ResponseEntity.ok(dishEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(
            path = "/restaurants/{restaurantId}/dishes/{dishId}"
    )
    public ResponseEntity<Void> deleteDish(@PathVariable("restaurantId") Integer restaurantId, @PathVariable("dishId") Integer dishId) {
        try {
            Optional<DishEntity> dish = dishCrud.findById(dishId);

            if (dish.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            dishCrud.delete(dish.get());

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(
            path = "/restaurants/{id}/dishes",
            produces = "application/json"
    )
    public ResponseEntity<?> getDishesByRestaurantId(@PathVariable("id") Integer restaurantId) {
        try {
            Optional<RestaurantEntity> restaurant = restaurantCrud.findById(restaurantId);

            return restaurant.map(value -> ResponseEntity.ok(value.getDishes()))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
