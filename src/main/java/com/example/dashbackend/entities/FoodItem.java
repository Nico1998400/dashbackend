package com.example.dashbackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_food_item")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String foodName;
    private String foodDescription;
    private int foodNumber;
    private Long price;

    @ManyToOne()
    @JoinColumn(name = "category_id" )
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "foodItem", fetch = FetchType.LAZY)
    private List<FoodItemExtraChoice> foodItemExtraChoices;
    @Override
    public String toString() {
        return "FoodItem{" +
                "id=" + id +
                ", foodName='" + foodName + '\'' +
                ", foodDescription='" + foodDescription + '\'' +
                ", foodNumber=" + foodNumber +
                ", price=" + price +
                // Don't include the 'category' and 'foodItemExtraChoices' fields to avoid recursion
                '}';
    }
}

