package com.example.springstarbucksapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springstarbucksapi.model.*;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    Optional<Drink> findByName(String name);
}
