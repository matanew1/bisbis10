package com.att.tdp.bisbis10.data;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

/*
    * The OrderEntity class represents an order in the database.
    * The class is annotated with @Entity to indicate that it is an entity class.
    * The @Table annotation specifies the name of the table in the database.
    * The class has fields for the id, restaurantId, and a list of order items.
    * The id field is annotated with @Id and @GeneratedValue to indicate that it is the primary key and is automatically generated.
    * The restaurantId field is annotated with @NotNull to indicate that it is required.
    * The items field is annotated with @OneToMany to indicate that it is a one-to-many relationship with the OrderItemEntity class.
    * The class has convenience methods addItem and removeItem to add and remove order items from the list.
    * The class has getters and setters for the fields.
 */
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();


    // Convenience methods
    public void addItem(OrderItemEntity item) {
        item.setOrder(this);
        items.add(item);
    }

    public void removeItem(OrderItemEntity item) {
        item.setOrder(null);
        items.remove(item);
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }

    // toString method
    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", items=" + items +
                '}';
    }
}
