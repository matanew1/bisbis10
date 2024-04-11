package com.att.tdp.bisbis10.dal;

import com.att.tdp.bisbis10.data.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingCrud extends JpaRepository<RatingEntity, Integer> {
}
