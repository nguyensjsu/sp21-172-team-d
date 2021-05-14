package com.example.springstarbucksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springstarbucksapi.model.*;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
