package com.example.springstarbucksapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springstarbucksapi.model.*;

public interface CardRepository extends JpaRepository<StarbucksCard, Long> {
    Optional<StarbucksCard> findByCardNumber(String cardNumber);
    List<StarbucksCard> findByCustomer(Customer customer);
}
