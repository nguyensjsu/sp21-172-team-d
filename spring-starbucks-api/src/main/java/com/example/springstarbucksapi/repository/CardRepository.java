package com.example.springstarbucksapi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<StarbucksCard, Long>{
    StarbucksCard findByCardNumber(String cardNumber);
}
