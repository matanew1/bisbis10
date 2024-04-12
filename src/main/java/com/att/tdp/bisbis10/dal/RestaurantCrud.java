package com.att.tdp.bisbis10.dal;


import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantCrud extends JpaRepository<RestaurantEntity, Integer> {
    // Find all restaurants by cuisine
    Iterable<RestaurantEntity> findByCuisinesContaining(String cuisine);
}
