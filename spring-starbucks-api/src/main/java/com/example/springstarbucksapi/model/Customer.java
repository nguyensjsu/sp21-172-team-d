package com.example.springstarbucksapi.model;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.Table;

import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
@RequiredArgsConstructor
public class Customer{
    private @Id String customerId;
    private Integer rewardsPoints;

    // @OneToMany
    // @JoinColumn(name="cardNumber")
    // private List<StarbucksCard> cards = new ArrayList<>();
    // public void addCard(StarbucksCard card){
    //     cards.add(card);
    // }
    @OneToMany(mappedBy = "customer", cascade={CascadeType.ALL})
	private List<StarbucksCard> starbucksCards;
    
}
    