package com.att.tdp.bisbis10.bondary;

import com.att.tdp.bisbis10.data.OrderEntity;
import com.att.tdp.bisbis10.data.OrderItemEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderBoundary {

    private Integer restaurantId;
    private OrderItemBoundary[] orderItems;

    public OrderBoundary(Integer restaurantId, OrderItemBoundary[] items) {
        this.restaurantId = restaurantId;
        this.orderItems = items;
    }

    public OrderEntity toEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setRestaurantId(this.restaurantId);
        return orderEntity;
    }

    public OrderItemBoundary[] getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItemBoundary[] orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderItemEntity> toEntity(OrderEntity orderEntity) {
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        for (OrderItemBoundary orderItem : this.orderItems) {
            OrderItemEntity orderItemEntity = orderItem.toEntity();
            orderItemEntity.setOrder(orderEntity);
            orderItemEntities.add(orderItemEntity);
        }
        return orderItemEntities;
    }




}
