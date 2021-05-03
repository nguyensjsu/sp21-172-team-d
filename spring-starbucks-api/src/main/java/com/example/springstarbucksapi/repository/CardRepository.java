package com.example.springstarbucksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springstarbucksapi.model.*;
public interface CardRepository extends JpaRepository<StarbucksCard, Long>{
    StarbucksCard findByCardNumber(String cardNumber);
}
