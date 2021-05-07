package com.example.springstarbucksapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springstarbucksapi.model.*;

public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findByName(String name);
}
