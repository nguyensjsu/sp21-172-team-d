package com.example.springstarbucksapi;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor  // required by Hibernate for Entity creation
@RequiredArgsConstructor
@Entity
@Table()
class Drink {
  	// public Drink(){}  // Default constructor required by Hibernate for Entity creation

	private @Id @GeneratedValue @Column(name = "DRINK_ID") Long id;
	
	@NotNull @NonNull private String name;
	
	@OneToMany(mappedBy = "drink", fetch = FetchType.EAGER)
	Set<Price> price;

}
