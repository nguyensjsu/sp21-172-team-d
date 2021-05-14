package com.example.springstarbucksapi.repository;

import com.example.springstarbucksapi.model.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
    Customer findByCustomerId(String customerId);
}
