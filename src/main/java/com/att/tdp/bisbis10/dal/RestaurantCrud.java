package com.att.tdp.bisbis10.dal;


import com.att.tdp.bisbis10.data.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*
    * This interface is used to interact with the database to perform CRUD operations on the RestaurantEntity class.
    * It extends the JpaRepository interface, which provides methods for common database operations such as save, findById, findAll, delete, etc.
    * The JpaRepository interface takes two parameters: the entity class (RestaurantEntity) and the type of the primary key (Integer).
    * The RestaurantCrud interface provides an additional method to find all restaurants by cuisine.
    * The method signature is findByCuisinesContaining(String cuisine), which returns an Iterable of RestaurantEntity.
    * The method name follows the Spring Data JPA naming convention, which allows Spring Data JPA to automatically generate the query based on the method name.
 */
public interface RestaurantCrud extends JpaRepository<RestaurantEntity, Integer> {
    // Find all restaurants by cuisine
    Iterable<RestaurantEntity> findByCuisinesContaining(String cuisine);
}
