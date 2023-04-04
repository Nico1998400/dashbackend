package com.example.dashbackend.services;

import com.example.dashbackend.entities.*;
import com.example.dashbackend.repository.ExtraChoiceRepository;
import com.example.dashbackend.repository.ExtraChoiceTypeRepository;
import com.example.dashbackend.repository.FoodItemExtraChoiceRepository;
import com.example.dashbackend.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtraChoiceService {


    private final ExtraChoiceRepository extraChoiceRepository;
    private final FoodItemRepository foodItemRepository;
    private final FoodItemExtraChoiceRepository foodItemExtraChoiceRepository;
    private final ExtraChoiceTypeRepository extraChoiceTypeRepository;
    public ExtraChoice createExtraChoice(ExtraChoice extraChoice, int foodItemId, User user) {
        FoodItem existingFoodItem = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new RuntimeException("FoodItem not found"));

        if (!existingFoodItem.getCategory().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("FoodItem does not belong to the user");
        }

        // Set the extraChoice property of each ExtraChoiceType entity
        for (ExtraChoiceType extraChoiceType : extraChoice.getExtraChoiceTypes()) {
            extraChoiceType.setExtraChoice(extraChoice);
        }

        extraChoiceRepository.save(extraChoice);

        FoodItemExtraChoice foodItemExtraChoice = FoodItemExtraChoice.builder()
                .foodItem(existingFoodItem)
                .extraChoice(extraChoice)
                .build();

        foodItemExtraChoiceRepository.save(foodItemExtraChoice);

        return extraChoice;
    }

    public List<ExtraChoice> getAllExtraChoices(User user) {
        List<ExtraChoice> extraChoices = extraChoiceRepository.findAll();

        extraChoices = extraChoices.stream()
                .filter(ec -> user.getId().equals(ec.getFoodItemExtraChoices().get(0).getFoodItem().getCategory().getUser().getId()))
                .collect(Collectors.toList());

        extraChoices.forEach(extraChoice -> {
            extraChoice.setExtraChoiceTypes(extraChoiceTypeRepository.findByExtraChoiceId(extraChoice.getId()));
        });

        return extraChoices;
    }
    public ExtraChoice updateExtraChoice(int id, ExtraChoice updatedExtraChoice, User user) {
        ExtraChoice extraChoice = getExtraChoiceById(id, user);

        extraChoice.setName(updatedExtraChoice.getName());

        // Create a map of updated ExtraChoiceType entities by id
        Map<Integer, ExtraChoiceType> updatedExtraChoiceTypeMap = updatedExtraChoice.getExtraChoiceTypes().stream()
                .filter(ect -> ect.getId() != 0)
                .collect(Collectors.toMap(ExtraChoiceType::getId, Function.identity()));

        // Update existing ExtraChoiceType entities
        extraChoice.getExtraChoiceTypes().removeIf(extraChoiceType -> !updatedExtraChoiceTypeMap.containsKey(extraChoiceType.getId()));
        for (ExtraChoiceType extraChoiceType : extraChoice.getExtraChoiceTypes()) {
            ExtraChoiceType updatedExtraChoiceType = updatedExtraChoiceTypeMap.get(extraChoiceType.getId());
            if (updatedExtraChoiceType != null) {
                extraChoiceType.setName(updatedExtraChoiceType.getName());
            }
        }

        // Add new ExtraChoiceType entities
        for (ExtraChoiceType extraChoiceType : updatedExtraChoice.getExtraChoiceTypes()) {
            if (extraChoiceType.getId() == 0) {
                extraChoiceType.setExtraChoice(extraChoice);
                extraChoice.getExtraChoiceTypes().add(extraChoiceType);
            }
        }

        return extraChoiceRepository.save(extraChoice);
    }
    private ExtraChoice getExtraChoiceById(int id, User user) {
        Optional<ExtraChoice> extraChoice = extraChoiceRepository.findById(id);
        return extraChoice
                .filter(ec -> user.getId().equals(ec.getFoodItemExtraChoices().get(0).getFoodItem().getCategory().getUser().getId()))
                .orElseThrow(() -> new RuntimeException("ExtraChoice not found"));
    }

}

