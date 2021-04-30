package com.example.springstarbucksapi;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardNumber(String cardNumber);
}
