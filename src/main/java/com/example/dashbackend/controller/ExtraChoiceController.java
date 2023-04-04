package com.example.dashbackend.controller;

import com.example.dashbackend.entities.ExtraChoice;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.services.ExtraChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping()
    public ResponseEntity<List<ExtraChoice>> getAllExtraChoices(@AuthenticationPrincipal User user) {
        List<ExtraChoice> extraChoices = extraChoiceService.getAllExtraChoices(user);
        return ResponseEntity.ok(extraChoices);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ExtraChoice> updateExtraChoice(
            @PathVariable int id,
            @RequestBody ExtraChoice updatedExtraChoice,
            @AuthenticationPrincipal User user) {
        ExtraChoice extraChoice = extraChoiceService.updateExtraChoice(id, updatedExtraChoice, user);
        return ResponseEntity.ok(extraChoice);
    }

}