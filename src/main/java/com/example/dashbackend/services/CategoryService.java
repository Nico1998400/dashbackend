package com.example.dashbackend.services;

import com.example.dashbackend.entities.Category;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.repository.CategoryRepository;
import com.example.dashbackend.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category, User user) {
        category.setUser(user);
        return categoryRepository.save(category);
    }

    public List<Category> getCategoriesForLoggedInUser(User user) {
        return categoryRepository.findByUserId(user.getId());
    }

    public void deleteCategory(int id, User user) {
        Category category = getCategoryById(id, user);
        if (category != null && user.getId().equals(category.getUser().getId())) {
            categoryRepository.deleteById(id);
        }
    }

    public Category getCategoryById(int id, User user) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.filter(c -> user.getId().equals(c.getUser().getId())).orElse(null);
    }

    public Category updateCategory(int id, Category updatedCategory, User user) {
        Category category = getCategoryById(id, user);
        if (category != null && user.getId().equals(category.getUser().getId())) {
            category.setCategoryTitle(updatedCategory.getCategoryTitle());
            return categoryRepository.save(category);
        }
        return null;
    }

    User getUserFromRequest(HttpServletRequest request) {
        String jwt = null;
        String userEmail = null;

        // First, try to get the JWT token from the cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    userEmail = jwtService.extractUsername(jwt);
                    break;
                }
            }
        }

        // If the JWT token was not found in the cookies, try to get it from the headers
        if (jwt == null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                userEmail = jwtService.extractUsername(jwt);
            }
        }

        if (userEmail == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

