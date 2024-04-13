package com.att.tdp.bisbis10.dal;

import com.att.tdp.bisbis10.data.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*
    * This interface is used to perform CRUD operations on the OrderItemEntity table.
    * It extends the JpaRepository interface, which provides the implementation of the CRUD operations.
 */
public interface OrderItemCrud extends JpaRepository<OrderItemEntity, Integer> {
}