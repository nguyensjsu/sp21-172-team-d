package com.example.springstarbucksapi;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok. RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "STARBUCKS_ORDERS")
public class Order {
    private @Id @GeneratedValue Long id;
    private String drink;
    private String milk;
    private String size;
    private double total;
    private String status;
}
