package com.example.springstarbucksapi.repository;

import com.example.springstarbucksapi.model.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Customer findByCustomerId(String customerId);
}
