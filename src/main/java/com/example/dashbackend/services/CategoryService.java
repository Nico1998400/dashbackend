package com.example.dashbackend.services;

import com.example.dashbackend.entities.Category;
import com.example.dashbackend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
    public List<Category> getCategoriesByUserId(int userId) {
        return categoryRepository.findByUserId(userId);
    }
}

