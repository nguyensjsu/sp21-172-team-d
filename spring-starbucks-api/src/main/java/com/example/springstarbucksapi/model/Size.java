package com.example.springstarbucksapi.model;

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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table()
public class Size {
  	public Size(){}  // Default constructor required by Hibernate for Entity creation

	private @Id @GeneratedValue @Column(name = "SIZE_ID") Long id;

	@NotNull @NonNull private String name;
	// @NotNull @NonNull private float volumeOz;  // Different for hot and cold drinks.. too complicated for this assignment
	
	@OneToMany(mappedBy = "size", fetch = FetchType.EAGER)
	Set<Price> price;
	

}
