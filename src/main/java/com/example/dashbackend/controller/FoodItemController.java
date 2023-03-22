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

@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
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

    @GetMapping
    public List<Category> getUserCategories(Authentication authentication) {
        return foodItemService.getUserCategories(authentication.getName());
    }

}
