package com.example.springstarbucksapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springstarbucksapi.model.*;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findByDrinkIgnoreCaseAndSizeIgnoreCase(String drink, String size);
}
