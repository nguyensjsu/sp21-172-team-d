package com.example.springstarbucksapi;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface DrinkRepository extends JpaRepository<Drink, Long> {
    Optional<Drink> findByName(String name);
}
