package com.example.dashbackend.controller;

import com.example.dashbackend.entities.FoodItem;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.services.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/fooditem")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemService;

    @PostMapping("/post")
    public ResponseEntity<FoodItem> createFoodItem(@RequestBody FoodItem foodItem, @AuthenticationPrincipal User user) {
        FoodItem savedFoodItem = foodItemService.createFoodItem(foodItem, user);
        return ResponseEntity.ok(savedFoodItem);
    }

    @GetMapping()
    public ResponseEntity<List<FoodItem>> getAllFoodItems(@AuthenticationPrincipal User user) {
        List<FoodItem> foodItems = foodItemService.getAllFoodItems(user);
        return ResponseEntity.ok(foodItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable int id, @AuthenticationPrincipal User user) {
        FoodItem foodItem = foodItemService.getFoodItemById(id, user);
        return ResponseEntity.ok(foodItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable int id, @RequestBody FoodItem updatedFoodItem, @AuthenticationPrincipal User user) {
        FoodItem foodItem = foodItemService.updateFoodItem(id, updatedFoodItem, user);
        return ResponseEntity.ok(foodItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable int id, @AuthenticationPrincipal User user) {
        foodItemService.deleteFoodItem(id, user);
        return ResponseEntity.noContent().build();
    }
}