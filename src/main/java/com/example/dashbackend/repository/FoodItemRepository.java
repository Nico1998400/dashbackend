package com.example.dashbackend.repository;

import com.example.dashbackend.entities.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {
    Optional<FoodItem> findFirstByCategoryId(int categoryId);
}
