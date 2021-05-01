package com.example.springstarbucksapi;

import com.example.springstarbucksapi.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Customer findByCustomerId(String customerId);
}
