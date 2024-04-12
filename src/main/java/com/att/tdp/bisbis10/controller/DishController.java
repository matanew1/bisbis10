package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.bondary.DishBoundary;
import com.att.tdp.bisbis10.dal.DishCrud;
import com.att.tdp.bisbis10.dal.RestaurantCrud;
import com.att.tdp.bisbis10.data.DishEntity;
import com.att.tdp.bisbis10.data.RatingEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(
            path = "/restaurants/{id}/dishes",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<DishEntity> addDish(@PathVariable("id") Integer restaurantId, @RequestBody DishBoundary dishBoundary) {
        Optional<RestaurantEntity> restaurant = restaurantCrud.findById(restaurantId);

        if (restaurant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DishEntity dish = dishBoundary.toEntity();
        dish.setRestaurant(restaurant.get());

        dish = dishCrud.save(dish);

        return ResponseEntity.ok(dish);

    }

    @PutMapping(
            path = "/restaurants/{restaurantId}/dishes/{dishId}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<DishEntity> updateDish(@PathVariable("restaurantId") Integer restaurantId, @PathVariable("dishId") Integer dishId, @RequestBody DishBoundary dishBoundary) {
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
    }

    @DeleteMapping(
            path = "/restaurants/{restaurantId}/dishes/{dishId}"
    )
    public ResponseEntity<Void> deleteDish(@PathVariable("restaurantId") Integer restaurantId, @PathVariable("dishId") Integer dishId) {
        Optional<DishEntity> dish = dishCrud.findById(dishId);

        if (dish.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        dishCrud.delete(dish.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping(
            path = "/restaurants/{id}/dishes",
            produces = "application/json"
    )
    public ResponseEntity<DishEntity[]> getDishesByRestaurantId(@PathVariable("id") Integer restaurantId) {
        Optional<RestaurantEntity> restaurant = restaurantCrud.findById(restaurantId);

        return restaurant
                .map(restaurantEntity -> ResponseEntity.ok(dishCrud.findByRestaurant(restaurantEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

}
