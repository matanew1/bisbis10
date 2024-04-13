package com.att.tdp.bisbis10.bondary;

import com.att.tdp.bisbis10.data.OrderItemEntity;

public class OrderItemBoundary {
    private Integer amount;
    private Integer dishId;

    public OrderItemBoundary() {
    }

    public OrderItemBoundary(Integer amount, Integer dishId) {
        this.amount = amount;
        this.dishId = dishId;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setDishId(Integer dishId) {
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
