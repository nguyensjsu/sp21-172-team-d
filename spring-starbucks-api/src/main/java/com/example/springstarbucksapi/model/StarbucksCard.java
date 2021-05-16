package com.example.springstarbucksapi.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.*;
@Entity
@Table(name="STARBUCKS_CARD")
@Data
@RequiredArgsConstructor
public class StarbucksCard {
    private @Id @GeneratedValue Long id;
    @Column(nullable = false) private String cardNumber;
    @Column(nullable = false) private String cardCode;
    @Column(nullable = false) private BigDecimal balance = new BigDecimal("0.00");
    @Column(nullable = false) private boolean active;
    @Column(nullable = false) private String status;

    @JsonIgnore 
    @ManyToOne
    @JoinColumn(name="customerId")
    private Customer customer;

}
