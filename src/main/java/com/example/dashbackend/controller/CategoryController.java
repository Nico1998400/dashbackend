package com.example.dashbackend.controller;

import com.example.dashbackend.entities.Category;
import com.example.dashbackend.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/post")
    public ResponseEntity<Category> createCategory(Category category, HttpServletRequest request) {
        Category savedCategory = categoryService.createCategory(category, request);
        return ResponseEntity.ok(savedCategory);
    }
    @GetMapping
    public ResponseEntity<List<Category>> getCategoriesForLoggedInUser(HttpServletRequest request) {
        List<Category> categories = categoryService.getCategoriesForLoggedInUser(request);
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id, HttpServletRequest request) {
        categoryService.deleteCategory(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id, HttpServletRequest request) {
        Category category = categoryService.getCategoryById(id, request);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category updatedCategory, HttpServletRequest request) {
        Category category = categoryService.updateCategory(id, updatedCategory, request);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }
}


