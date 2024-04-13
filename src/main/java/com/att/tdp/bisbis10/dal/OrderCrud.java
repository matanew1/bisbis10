package com.att.tdp.bisbis10.dal;

import com.att.tdp.bisbis10.data.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*
    * This interface extends JpaRepository to provide CRUD operations for OrderEntity.
    * The OrderEntity class is the entity type to be managed and Integer is the type of the ID field.
 */
public interface OrderCrud extends JpaRepository<OrderEntity, Integer> {
}