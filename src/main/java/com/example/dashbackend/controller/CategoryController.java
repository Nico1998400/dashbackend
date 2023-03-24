package com.example.dashbackend.controller;

import com.example.dashbackend.entities.Category;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/post")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category, @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        System.out.println("Received category: " + category + user);
        Category savedCategory = categoryService.createCategory(category, user);
        return ResponseEntity.ok(savedCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategoriesForLoggedInUser(@AuthenticationPrincipal User user) {
        List<Category> categories = categoryService.getCategoriesForLoggedInUser(user);
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id, @AuthenticationPrincipal User user) {
        categoryService.deleteCategory(id, user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id, @AuthenticationPrincipal User user) {
        Category category = categoryService.getCategoryById(id, user);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category updatedCategory, @AuthenticationPrincipal User user) {
        Category category = categoryService.updateCategory(id, updatedCategory, user);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }
}


