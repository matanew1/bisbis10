package com.att.tdp.bisbis10.data;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")


/**
 * ORDER ITEMS
 * {
 * "restaurantId": 2,
 * orderItems:[
     * {"dishId":12,amount:1},
     * {"dishId":14,amount:1}
 * ]
 * }
 *
 */

public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount")
    public Integer amount;

    // One order item belongs to one dish
    @Column(name = "dish_id")
    private Integer dishId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order = new OrderEntity();

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

}
