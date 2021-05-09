package com.example.springstarbucksapi.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name="STARBUCKS_CARD", indexes=@Index(name = "altIndex", columnList = "cardNumber", unique = true))
public class StarbucksCard {

	private @Id @GeneratedValue Long id;
	
	@Column(nullable=false)	private String cardNumber;
	@Column(nullable=false)	private String cardCode;
	@Column(nullable=false)	private BigDecimal balance = new BigDecimal("0.00");
	@Column(nullable=false)	private boolean activated;
							private String status;
	@ManyToOne
	@JoinColumn(name="customerId")
	private Customer customer;

	public StarbucksCard(BigDecimal initialBalance, boolean activated, String status) {
        Random random = new Random();
        this.cardNumber = String.valueOf(random.nextInt(900000000) + 100000000);
        this.cardCode = String.valueOf(random.nextInt(900) + 100);
		this.balance = initialBalance;
		this.activated = activated;
		this.status = status;
	}
	
	public StarbucksCard(String initialBalance, boolean activated, String status) {
		this(new BigDecimal(initialBalance), activated, status);
	}
	
	public BigDecimal withdraw(BigDecimal amount) {
		// TODO: Issue #17 Handle insufficient funds
		balance = balance.subtract(amount);
		return balance;
	}
}
