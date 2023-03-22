package com.example.dashbackend.controller;

import com.example.dashbackend.entities.Category;
import com.example.dashbackend.entities.FoodItem;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.services.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/fooditem")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemService;

    @PostMapping("/post")
    public ResponseEntity<FoodItem> createFoodItem(@RequestBody FoodItem foodItem, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        FoodItem savedFoodItem = foodItemService.createFoodItem(foodItem, user);
        return ResponseEntity.ok(savedFoodItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable int id) {
        FoodItem foodItem = foodItemService.getFoodItemById(id);
        return ResponseEntity.ok(foodItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable int id, @RequestBody FoodItem updatedFoodItem) {
        FoodItem foodItem = foodItemService.updateFoodItem(id, updatedFoodItem);
        return ResponseEntity.ok(foodItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable int id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }

}