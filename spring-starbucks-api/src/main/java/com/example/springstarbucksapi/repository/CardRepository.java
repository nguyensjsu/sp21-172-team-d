package com.example.springstarbucksapi.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.example.springstarbucksapi.model.*;
public interface CardRepository extends CrudRepository<StarbucksCard, Long>{
    StarbucksCard findByCardNumber(String cardNumber);
    List<StarbucksCard> findByCustomer(Customer customer);
}
