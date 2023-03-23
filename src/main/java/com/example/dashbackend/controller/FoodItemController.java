package com.example.dashbackend.controller;

import com.example.dashbackend.entities.FoodItem;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.services.FoodItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/fooditem")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemService;

    @PostMapping("/post")
    public ResponseEntity<FoodItem> createFoodItem(@RequestBody FoodItem foodItem, HttpServletRequest request) {
        FoodItem savedFoodItem = foodItemService.createFoodItem(foodItem, request);
        return ResponseEntity.ok(savedFoodItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable int id, HttpServletRequest request) {
        FoodItem foodItem = foodItemService.getFoodItemById(id, request);
        return ResponseEntity.ok(foodItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable int id, @RequestBody FoodItem updatedFoodItem, HttpServletRequest request) {
        FoodItem foodItem = foodItemService.updateFoodItem(id, updatedFoodItem, request);
        return ResponseEntity.ok(foodItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable int id, HttpServletRequest request) {
        foodItemService.deleteFoodItem(id, request);
        return ResponseEntity.noContent().build();
    }
}