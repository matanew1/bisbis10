package com.att.tdp.bisbis10.dal;

import com.att.tdp.bisbis10.data.DishEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishCrud extends JpaRepository<DishEntity, Integer> {
}