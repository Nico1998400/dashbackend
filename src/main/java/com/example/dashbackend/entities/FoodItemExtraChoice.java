package com.example.dashbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_food_item_extra_choice")
public class FoodItemExtraChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    @ManyToOne
    @JoinColumn(name = "extra_choice_id")
    private ExtraChoice extraChoice;
}