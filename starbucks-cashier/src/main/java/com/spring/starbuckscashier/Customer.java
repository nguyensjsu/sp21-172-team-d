package com.spring.starbuckscashier;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Customer{
    private String customerId;
    private Integer rewardsPoints;
}
    