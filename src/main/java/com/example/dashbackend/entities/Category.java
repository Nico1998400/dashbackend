package com.example.dashbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String categoryTitle;

    @ManyToOne()
    @JoinColumn(name = "user_id" )
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<FoodItem> foodItems;
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryTitle='" + categoryTitle + '\'' +
                // Don't include the 'user' and 'foodItems' fields to avoid recursion
                '}';
    }
}
