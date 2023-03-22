package com.example.dashbackend.repository;

import com.example.dashbackend.entities.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {

}
