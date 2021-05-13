package com.example.springstarbucksapi.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor  // required by Hibernate for Entity creation
@RequiredArgsConstructor
@Entity
@Table(uniqueConstraints={
  @UniqueConstraint(columnNames = {"drink", "size", "price"})
})
public class Price {
  	// public Price(){}  // Default constructor required by Hibernate for Entity creation
    
    public Price(String drink, String size, String price) {
      this.drink = drink;
      this.size = size;
      this.price = new BigDecimal(price);
    }
    
    public Price(String drink, String size, Double price) {
      this.drink = drink;
      this.size = size;
      this.price = new BigDecimal(price);
    }
    
    public Price(String drink, String size, String price, int rewardsPrice) {
      this.drink = drink;
      this.size = size;
      this.price = new BigDecimal(price);
      this.rewardsPrice = rewardsPrice;
    }

    public Price(String drink, String size, Double price, int rewardsPrice) {
      this.drink = drink;
      this.size = size;
      this.price = new BigDecimal(price);
      this.rewardsPrice = rewardsPrice;
    }

    private @Id @GeneratedValue Long id;

    @NotNull @NonNull String drink;
    @NotNull @NonNull String size;

    // https://stackoverflow.com/questions/3730019/why-not-use-double-or-float-to-represent-currency
    @NotNull @NonNull BigDecimal price;
    
    int rewardsPrice;
}
