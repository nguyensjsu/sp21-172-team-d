package com.example.springstarbucksapi;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findByName(String name);
}
