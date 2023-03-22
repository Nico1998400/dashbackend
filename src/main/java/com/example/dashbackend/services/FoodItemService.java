package com.example.dashbackend.services;

import com.example.dashbackend.entities.Category;
import com.example.dashbackend.entities.FoodItem;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.repository.CategoryRepository;
import com.example.dashbackend.repository.FoodItemRepository;
import com.example.dashbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final FoodItemRepository foodItemRepository;

    public FoodItem createFoodItem(FoodItem foodItem, User user) {
        Category existingCategory = categoryRepository.findById(foodItem.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setUser(user);
        foodItem.setCategory(existingCategory);
        return foodItemRepository.save(foodItem);
    }

    public List<Category> getUserCategories(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return categoryService.findByUserIdWithFoodItems(user.getId());
    }
}