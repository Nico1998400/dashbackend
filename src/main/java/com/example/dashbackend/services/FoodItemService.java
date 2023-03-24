package com.example.dashbackend.services;

import com.example.dashbackend.entities.Category;
import com.example.dashbackend.entities.FoodItem;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.repository.CategoryRepository;
import com.example.dashbackend.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final CategoryRepository categoryRepository;
    private final FoodItemRepository foodItemRepository;

    public FoodItem createFoodItem(FoodItem foodItem, User user) {
        Category existingCategory = categoryRepository.findById(foodItem.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setUser(user);
        foodItem.setCategory(existingCategory);
        return foodItemRepository.save(foodItem);
    }

    public List<FoodItem> getAllFoodItems(User user) {
        List<FoodItem> foodItems = foodItemRepository.findAll();
        return foodItems.stream()
                .filter(f -> user.getId().equals(f.getCategory().getUser().getId()))
                .collect(Collectors.toList());
    }

    public FoodItem getFoodItemById(int id, User user) {
        Optional<FoodItem> foodItem = foodItemRepository.findById(id);
        return foodItem.filter(f -> user.getId().equals(f.getCategory().getUser().getId())).orElseThrow(() -> new RuntimeException("FoodItem not found"));
    }

    public FoodItem updateFoodItem(int id, FoodItem updatedFoodItem, User user) {
        FoodItem foodItem = getFoodItemById(id, user);

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

    public void deleteFoodItem(int id, User user) {
        FoodItem foodItem = getFoodItemById(id, user);
        if (user.getId().equals(foodItem.getCategory().getUser().getId())) {
            foodItemRepository.deleteById(id);
        }
    }

}