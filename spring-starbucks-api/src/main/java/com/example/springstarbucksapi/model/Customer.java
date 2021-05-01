package com.example.springstarbucksapi;

import java.util.Set;
import java.util.List;
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
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@RequiredArgsConstructor
public class Customer{
    private @Id String customerId;
    private Integer rewardsPoints;
    @OneToMany(fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    @JoinColumn(name="cardNumber")
    private List<StarbucksCard> cards;

}