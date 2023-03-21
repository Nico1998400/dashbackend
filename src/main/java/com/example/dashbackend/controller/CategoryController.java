package com.example.dashbackend.controller;

import com.example.dashbackend.entities.Category;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.repository.CategoryRepository;
import com.example.dashbackend.repository.UserRepository;
import com.example.dashbackend.services.CategoryService;
import com.example.dashbackend.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    @Autowired
    private CategoryRepository categoryRepository;
    @PostMapping("/post")
    public ResponseEntity<Category> createCategory(@RequestBody Category category, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        category.setUser(user);
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }
    @GetMapping
    public ResponseEntity<List<Category>> getCategoriesForLoggedInUser(HttpServletRequest request) {
        User loggedInUser = getUserFromRequest(request);
        List<Category> categories = categoryService.getCategoriesByUserId(loggedInUser.getId());
        return ResponseEntity.ok(categories);
    }


    private User getUserFromRequest(HttpServletRequest request) {
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

        System.out.println("JWT token: " + jwt);
        System.out.println("User email: " + userEmail);

        if (userEmail == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}