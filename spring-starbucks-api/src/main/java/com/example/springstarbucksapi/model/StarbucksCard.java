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
    @Column(nullable = false) private double balance;
    @Column(nullable = false) private boolean active;
    @Column(nullable = false) private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customerId")
    private Customer customer;

}
