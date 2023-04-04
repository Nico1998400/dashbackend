package com.example.dashbackend.repository;

import com.example.dashbackend.entities.ExtraChoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraChoiceTypeRepository extends JpaRepository<ExtraChoiceType, Integer> {
    List<ExtraChoiceType> findByExtraChoiceId(int extraChoiceId);

}

