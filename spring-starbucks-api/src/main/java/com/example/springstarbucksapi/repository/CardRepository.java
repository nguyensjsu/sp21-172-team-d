package com.example.springstarbucksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.example.springstarbucksapi.model.*;
public interface CardRepository extends JpaRepository<StarbucksCard, Long>{
    StarbucksCard findByCardNumber(String cardNumber);
    List<StarbucksCard> findByCustomer(Customer customer);
}
