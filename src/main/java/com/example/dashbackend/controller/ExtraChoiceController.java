package com.example.dashbackend.controller;

import com.example.dashbackend.entities.ExtraChoice;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.services.ExtraChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/extrachoice")
@RequiredArgsConstructor
public class ExtraChoiceController {
    private final ExtraChoiceService extraChoiceService;

    @PostMapping("/extra-choices/{foodItemId}")
    public ResponseEntity<?> createExtraChoice(
            @PathVariable("foodItemId") int foodItemId,
            @RequestBody ExtraChoice extraChoice,
            @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        ExtraChoice savedExtraChoice = extraChoiceService.createExtraChoice(extraChoice, foodItemId, user);
        return ResponseEntity.ok(savedExtraChoice);
    }
}