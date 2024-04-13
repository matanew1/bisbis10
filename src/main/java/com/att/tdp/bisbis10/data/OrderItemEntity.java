package com.att.tdp.bisbis10.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/*
    * The OrderItemEntity class represents an order item entity.
    * An order item is a dish that is part of an order.
    * It contains the following properties:
    * - id: The order item ID
    * - amount: The amount of the dish
    * - dishId: The dish ID
    * - order: The order entity
    * The class is annotated with JPA annotations to define the mapping with the order_items table in the database.
    * The order_items table contains the following columns:
    * - id: The order item ID (primary key)
    * - amount: The amount of the dish
    * - dish_id: The dish ID
    * - order_id: The order ID (foreign key)
 */
@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "dish_id")
    private Integer dishId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore // Ignore the order property during serialization to break the circular reference
    private OrderEntity order;

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

