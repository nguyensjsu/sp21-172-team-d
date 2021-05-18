package com.example.springstarbucksapi.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.RequiredArgsConstructor;

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

	  public StarbucksCard(BigDecimal initialBalance, boolean active, String status) {
        Random random = new Random();
        this.cardNumber = String.valueOf(random.nextInt(900000000) + 100000000);
        this.cardCode = String.valueOf(random.nextInt(900) + 100);
		    this.balance = initialBalance;
		    this.active = active;
		    this.status = status;
    }
	
	public StarbucksCard(String initialBalance, boolean active, String status) {
	    this(new BigDecimal(initialBalance), active, status);
	}
	
	public BigDecimal withdraw(BigDecimal amount) {
		// TODO: Issue #17 Handle insufficient funds
		balance = balance.subtract(amount);
		return balance;
	}
}
