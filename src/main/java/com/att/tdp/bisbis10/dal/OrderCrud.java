package com.att.tdp.bisbis10.dal;

import com.att.tdp.bisbis10.data.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCrud extends JpaRepository<OrderEntity, Integer> {
}