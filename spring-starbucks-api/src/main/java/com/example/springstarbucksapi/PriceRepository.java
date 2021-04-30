package com.example.springstarbucksapi;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findByDrinkIgnoreCaseAndSizeIgnoreCase(String drink, String size);
}
