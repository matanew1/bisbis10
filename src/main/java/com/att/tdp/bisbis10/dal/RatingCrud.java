package com.att.tdp.bisbis10.dal;

import com.att.tdp.bisbis10.data.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;


/*
    * This interface is used to perform CRUD operations on the RatingEntity table.
    * It extends the JpaRepository interface, which provides methods for common CRUD operations.
 */
public interface RatingCrud extends JpaRepository<RatingEntity, Integer> {
}
