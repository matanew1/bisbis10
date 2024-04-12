package com.att.tdp.bisbis10.bondary;

import com.att.tdp.bisbis10.data.OrderItemEntity;

public class OrderItemBoundary {
    private int amount;
    private int dishId;

    public OrderItemBoundary() {
    }

    public OrderItemBoundary(Integer amount, Integer dishId) {
        this.amount = amount;
        this.dishId = dishId;
    }

    public int getAmount() {
        return amount;
    }

    public int getDishId() {
        return dishId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public OrderItemEntity toEntity() {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setAmount(this.amount);
        orderItemEntity.setDishId(this.dishId);
        return orderItemEntity;
    }

    @Override
    public String toString() {
        return "OrderItemBoundary{" +
                "amount=" + amount +
                ", dishId=" + dishId +
                '}';
    }
}
