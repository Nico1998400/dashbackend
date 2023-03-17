package com.example.dashbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_extra_choice")
public class ExtraChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "extra_choice_type_id")
    private ExtraChoiceType type;

    @OneToMany(mappedBy = "extraChoice")
    private List<FoodItemExtraChoice> foodItemExtraChoices;
}

