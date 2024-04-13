package com.att.tdp.bisbis10.dal;

import com.att.tdp.bisbis10.data.DishEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DishCrud is a repository interface that extends JpaRepository for CRUD operations on DishEntity.
 * JpaRepository provides methods for performing CRUD operations on the entity.
 * JPA = Java Persistence API, a Java specification for accessing, persisting, and managing data between Java objects and a relational database.
 */
public interface DishCrud extends JpaRepository<DishEntity, Integer> {
}