package com.att.tdp.bisbis10.bondary;

import com.att.tdp.bisbis10.data.OrderEntity;
import com.att.tdp.bisbis10.data.OrderItemEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderBoundary {

    private int restaurantId;
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    public OrderBoundary() {
    }

    public OrderBoundary(int restaurantId, List<OrderItemEntity> items) {
        this.restaurantId = restaurantId;
        this.orderItems = items;
    }

    // Getters and setters
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderItemEntity> getItems() {
        return orderItems;
    }

    public void setItems(List<OrderItemEntity> items) {
        this.orderItems = items;
    }

    public OrderEntity toEntity() {
        OrderEntity order = new OrderEntity();
        order.setRestaurantId(this.restaurantId);
        // Set the order reference for each order item
        for (OrderItemEntity item : this.orderItems) {
            item.setOrder(order);
        }
        order.setItems(this.orderItems);
        return order;
    }
}
