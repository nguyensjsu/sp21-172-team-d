package com.example.springstarbucksapi.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "DrinkOrder")  // lol order is not a good table name
@RequiredArgsConstructor
public class Order {

    private @Id @GeneratedValue Long id;
	private String drink;
	private String milk;
	private String size;
	private BigDecimal total;
	private String status;

	// @ManyToOne
	// Register register;

}
