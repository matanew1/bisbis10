package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.bondary.OrderBoundary;
import com.att.tdp.bisbis10.bondary.OrderItemBoundary;
import com.att.tdp.bisbis10.dal.DishCrud;
import com.att.tdp.bisbis10.dal.OrderCrud;
import com.att.tdp.bisbis10.dal.OrderItemCrud;
import com.att.tdp.bisbis10.data.OrderEntity;
import com.att.tdp.bisbis10.data.OrderItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path="/api")
public class OrderController {

    @Autowired
    private OrderCrud orderCrud;

    @Autowired
    private OrderItemCrud orderItemCrud;
    @Autowired
    private DishCrud dishCrud;

    @PostMapping(
            path = "/order",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<OrderEntity> addOrder(@RequestBody OrderBoundary orderBoundary) {
        int restaurantId = orderBoundary.getRestaurantId();
        OrderItemBoundary[] orderItems = orderBoundary.getOrderItems();

        if (restaurantId == 0 || orderItems == null || orderItems.length == 0) {
            return ResponseEntity.badRequest().build();
        }

        for (OrderItemBoundary orderItem : orderItems) {
            if (orderItem.getAmount() < 0 || orderItem.getDishId() == 0 ) {
                return ResponseEntity.badRequest().build();
            }
            // if order.getRestaurantId() not exist in the database
            int dishId = orderItem.getDishId();

            // if dishId not exist in the database
            if (dishCrud.findById(dishId).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }

        OrderEntity orderEntity = orderBoundary.toEntity();
        orderEntity = orderCrud.save(orderEntity);

        List<OrderItemEntity> orderItemEntities = orderBoundary.toEntity(orderEntity);
        orderItemEntities = orderItemCrud.saveAll(orderItemEntities);

        orderEntity.setItems(orderItemEntities);

        return ResponseEntity.ok(orderEntity);

    }

}
