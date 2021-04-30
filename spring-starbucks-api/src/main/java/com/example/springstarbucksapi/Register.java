package com.example.springstarbucksapi;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table()
class Register {
    // public Register(){}  // Default constructor required by Hibernate for Entity creation

    public Register(long regid) {
        this.id = regid;
    }

    private @Id long id;

    // @OneToMany
    // Set<Order> orderHistory;

    // @ManyToOne
    // @JoinColumn()
    @OneToOne  // TODO: When an order is deleted, its foreign key needs to be removed from this field
    Order order;
    // long activeOrderId;

}
