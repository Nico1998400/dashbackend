package com.example.dashbackend.services;

import com.example.dashbackend.entities.ExtraChoice;
import com.example.dashbackend.entities.FoodItem;
import com.example.dashbackend.entities.FoodItemExtraChoice;
import com.example.dashbackend.entities.User;
import com.example.dashbackend.repository.ExtraChoiceRepository;
import com.example.dashbackend.repository.FoodItemExtraChoiceRepository;
import com.example.dashbackend.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtraChoiceService {


    private final ExtraChoiceRepository extraChoiceRepository;
    private final FoodItemRepository foodItemRepository;
    private final FoodItemExtraChoiceRepository foodItemExtraChoiceRepository;

    public ExtraChoice createExtraChoice(ExtraChoice extraChoice, int foodItemId, User user) {
        FoodItem existingFoodItem = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new RuntimeException("FoodItem not found"));

        if (!existingFoodItem.getCategory().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("FoodItem does not belong to the user");
        }

        extraChoiceRepository.save(extraChoice);

        FoodItemExtraChoice foodItemExtraChoice = FoodItemExtraChoice.builder()
                .foodItem(existingFoodItem)
                .extraChoice(extraChoice)
                .build();

        foodItemExtraChoiceRepository.save(foodItemExtraChoice);

        return extraChoice;
    }

}

