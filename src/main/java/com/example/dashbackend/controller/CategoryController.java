package com.example.dashbackend.controller;

import com.example.dashbackend.entities.Category;
import com.example.dashbackend.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/post")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }

}
