package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.bondary.OrderBoundary;
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

import java.util.List;

@Controller
@RequestMapping(path="/api")
public class OrderController {

    @Autowired
    private OrderCrud orderCrud;

    @Autowired
    private OrderItemCrud orderItemCrud;

    @PostMapping(
            path = "/order",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<OrderEntity> addOrder(@RequestBody OrderBoundary orderBoundary) {
        // Create and save the OrderEntity
        OrderEntity orderEntity = orderBoundary.toEntity();
        // Save the OrderEntity
        orderEntity = orderCrud.save(orderEntity);

        // Retrieve the list of OrderItemEntities from the OrderBoundary
        List<OrderItemEntity> orderItems = orderBoundary.getItems();
        // Iterate through the OrderItemEntities and set the reference to the OrderEntity
        for (OrderItemEntity orderItem : orderItems) {
            // Ensure the OrderEntity reference is set
            orderItem.setOrder(orderEntity);
            // Save each OrderItemEntity
            orderItemCrud.save(orderItem);
        }

        return ResponseEntity.ok(orderEntity);
    }
}
