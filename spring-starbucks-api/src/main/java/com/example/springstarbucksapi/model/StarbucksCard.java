package com.example.springstarbucksapi.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(indexes=@Index(name = "altIndex", columnList = "cardNumber", unique = true))
public class StarbucksCard {

	private @Id @GeneratedValue Long id;
	
	@Column(nullable=false)	private String cardNumber;
	@Column(nullable=false)	private String cardCode;
	@Column(nullable=false)	private BigDecimal balance;
	@Column(nullable=false)	private boolean activated;
							private String status;
							
}

/*




See:  https://www.codementor.io/codehakase/building-a-restful-api-with-golang-a6yivzqdo

*/
