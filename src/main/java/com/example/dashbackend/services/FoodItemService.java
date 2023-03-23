package com.example.dashbackend.services;

import com.example.dashbackend.entities.Category;
import com.example.dashbackend.entities.FoodItem;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.repository.CategoryRepository;
import com.example.dashbackend.repository.FoodItemRepository;
import com.example.dashbackend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final FoodItemRepository foodItemRepository;

    public FoodItem createFoodItem(FoodItem foodItem, HttpServletRequest request) {
        User user = categoryService.getUserFromRequest(request);
        Category existingCategory = categoryRepository.findById(foodItem.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setUser(user);
        foodItem.setCategory(existingCategory);
        return foodItemRepository.save(foodItem);
    }

    public FoodItem getFoodItemById(int id, HttpServletRequest request) {
        User user = categoryService.getUserFromRequest(request);
        Optional<FoodItem> foodItem = foodItemRepository.findById(id);
        return foodItem.filter(f -> user.getId().equals(f.getCategory().getUser().getId())).orElseThrow(() -> new RuntimeException("FoodItem not found"));
    }

    public FoodItem updateFoodItem(int id, FoodItem updatedFoodItem, HttpServletRequest request) {
        User user = categoryService.getUserFromRequest(request);
        FoodItem foodItem = getFoodItemById(id, request);

        foodItem.setFoodName(updatedFoodItem.getFoodName());
        foodItem.setFoodDescription(updatedFoodItem.getFoodDescription());
        foodItem.setFoodNumber(updatedFoodItem.getFoodNumber());
        foodItem.setPrice(updatedFoodItem.getPrice());

        Category existingCategory = categoryRepository.findById(updatedFoodItem.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setUser(foodItem.getCategory().getUser());
        foodItem.setCategory(existingCategory);

        return foodItemRepository.save(foodItem);
    }

    public void deleteFoodItem(int id, HttpServletRequest request) {
        User user = categoryService.getUserFromRequest(request);
        FoodItem foodItem = getFoodItemById(id, request);
        if (user.getId().equals(foodItem.getCategory().getUser().getId())) {
            foodItemRepository.deleteById(id);
        }
    }


}
