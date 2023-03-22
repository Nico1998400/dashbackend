package com.example.dashbackend.repository;

import java.util.Optional;

import com.example.dashbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);

}
