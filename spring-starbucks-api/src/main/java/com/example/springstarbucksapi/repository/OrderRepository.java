package com.example.springstarbucksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.example.springstarbucksapi.model.Order;
public interface OrderRepository extends CrudRepository<Order, Long>{
    
}
