package com.example.springstarbucksapi.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Random;

import com.example.springstarbucksapi.model.*;
import com.example.springstarbucksapi.repository.*;

@RestController
public class CustomerController {
    private final CustomerRepository repository;
    
    @Autowired
    private CardRepository cardRepository; 
    
    CustomerController(CustomerRepository repository){
        this.repository = repository;
    }

    @CrossOrigin(origins = "http://localhost:8900")
    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    Customer newCustomer(@RequestBody Customer customer){ //Needs to contain the customerId in the body of the request
        System.out.println("Creating Customer: #" + customer.getCustomerId());
        customer.setRewardsPoints(0);

        StarbucksCard newCard = new StarbucksCard();

        // TODO: Issue #18 Consolidate new card logic
        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 100;
        newCard.setCardNumber(String.valueOf(num));
        newCard.setCardCode(String.valueOf(code));
        newCard.setBalance(new BigDecimal("20.00"));
        newCard.setActivated(true);
        newCard.setStatus("New Card");
        newCard.setCustomer(customer);
        //customer.addCard(newCard);
        Customer new_customer = repository.save(customer);

        return new_customer;
    }

    @PostMapping("/customer/card")
    @ResponseStatus(HttpStatus.CREATED)
    StarbucksCard addCard(@RequestBody Customer customer){
        System.out.println("Creating Card for Customer: #" + customer.getCustomerId());

        // TODO: #18
        StarbucksCard newCard = new StarbucksCard();
        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 100;
        newCard.setCardNumber(String.valueOf(num));
        newCard.setCardCode(String.valueOf(code));
        newCard.setBalance(new BigDecimal("20.00"));
        newCard.setActivated(true);
        newCard.setStatus("New Card");
        newCard.setCustomer(customer);

        System.out.println("Card information " + newCard);
        cardRepository.save(newCard);
        return newCard;

     }


    @GetMapping("/customer")
    List<Customer> getCustomers(){
        return repository.findAll();
    }

    @GetMapping("/customer/{customerId}")
    Customer getCustomer(@PathVariable String customerId){
        Customer customer = repository.findByCustomerId(customerId);
        if(customer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found.");
        return customer;
    }

    @GetMapping("/customer/cards")
    List<StarbucksCard> getCards(@RequestBody Customer customer){
        return cardRepository.findByCustomer(customer);
    }
}