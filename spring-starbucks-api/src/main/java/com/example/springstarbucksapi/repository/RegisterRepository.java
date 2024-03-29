package com.example.springstarbucksapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.springstarbucksapi.model.*;

public interface RegisterRepository extends JpaRepository<Register, Long> {
    Optional<Register> findById(long id);

    @Transactional
    @Modifying
    @Query("UPDATE Register SET order_id = NULL")
    void disassociateFromAllOrders();
}
