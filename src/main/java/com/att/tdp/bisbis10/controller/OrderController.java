package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.bondary.OrderBoundary;
import com.att.tdp.bisbis10.bondary.OrderItemBoundary;
import com.att.tdp.bisbis10.dal.RestaurantCrud;
import com.att.tdp.bisbis10.data.DishEntity;
import com.att.tdp.bisbis10.dal.DishCrud;
import com.att.tdp.bisbis10.dal.OrderCrud;
import com.att.tdp.bisbis10.dal.OrderItemCrud;
import com.att.tdp.bisbis10.data.OrderEntity;
import com.att.tdp.bisbis10.data.OrderItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api")
public class OrderController {

    @Autowired
    private OrderCrud orderCrud;

    @Autowired
    private OrderItemCrud orderItemCrud;

    @Autowired
    private DishCrud dishCrud;
    @Autowired
    private RestaurantCrud restaurantCrud;

    @PostMapping(
            path = "/order",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> addOrder(@RequestBody OrderBoundary orderBoundary) {
        int restaurantId = orderBoundary.getRestaurantId();
        OrderItemBoundary[] orderItems = orderBoundary.getOrderItems();

        if (restaurantId == 0 || orderItems == null || orderItems.length == 0) {
            return ResponseEntity.badRequest().body("Invalid request: restaurantId and orderItems are required.");
        }

        if (restaurantCrud.findById(restaurantId).isEmpty()) {
            return ResponseEntity.badRequest().body("Restaurant with ID " + restaurantId + " not found.");
        }

        for (OrderItemBoundary orderItem : orderItems) {
            if (orderItem.getAmount() <= 0 || orderItem.getDishId() == 0) {
                return ResponseEntity.badRequest().body("Invalid order item: amount must be positive and dishId must be provided.");
            }

            Optional<DishEntity> dishOptional = dishCrud.findById(orderItem.getDishId());
            if (dishOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Dish with ID " + orderItem.getDishId() + " not found.");
            }
        }

        try {
            OrderEntity orderEntity = orderBoundary.toEntity();
            orderEntity = orderCrud.save(orderEntity);

            List<OrderItemEntity> orderItemEntities = orderBoundary.toEntity(orderEntity);
            orderItemEntities = orderItemCrud.saveAll(orderItemEntities);

            orderEntity.setItems(orderItemEntities);

            return ResponseEntity.ok(orderEntity);
        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the order: " + e.getMessage());
        }
    }
}
