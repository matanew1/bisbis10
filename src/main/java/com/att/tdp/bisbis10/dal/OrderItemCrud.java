package com.att.tdp.bisbis10.dal;

import com.att.tdp.bisbis10.data.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemCrud extends JpaRepository<OrderItemEntity, Integer> {
}