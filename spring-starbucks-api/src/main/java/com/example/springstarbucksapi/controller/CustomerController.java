package com.example.springstarbucksapi.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import com.example.springstarbucksapi.model.*;
import com.example.springstarbucksapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 100;
        newCard.setCardNumber(String.valueOf(num));
        newCard.setCardCode(String.valueOf(code));
        newCard.setBalance(new BigDecimal(0.0));
        newCard.setActive(true);
        newCard.setStatus("New Card");
        newCard.setCustomer(customer);

        //create new card list 
        List<StarbucksCard> cards = new ArrayList<>();
        cards.add(newCard);
        customer.setStarbucksCards(cards);

        Customer new_customer = repository.save(customer);
        return new_customer;
    }

    @CrossOrigin(origins = "http://localhost:8900")
    @PostMapping("/customer/card/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    StarbucksCard addCard(@PathVariable String customerId){
        System.out.println("Creating Card for Customer: #" + customerId);
        Customer customer = repository.findByCustomerId(customerId);

        if(customer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found.");

        StarbucksCard newCard = new StarbucksCard();
        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 100;
        newCard.setCardNumber(String.valueOf(num));
        newCard.setCardCode(String.valueOf(code));
        newCard.setBalance(new BigDecimal(0.0));
        newCard.setActive(true);
        newCard.setStatus("New Card");
        newCard.setCustomer(customer);

        List<StarbucksCard> cards = customer.getStarbucksCards();
        cards.add(newCard);
        customer.setStarbucksCards(cards);
        System.out.println("Card information " + newCard);
    
        repository.save(customer);
        return newCard;

     }

    @GetMapping("/customer")
    List<Customer> getCustomers(){
        return (List<Customer>) repository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:8900")
    @GetMapping("/customer/{customerId}")
    Customer getCustomer(@PathVariable String customerId){
        Customer customer = repository.findByCustomerId(customerId);

        if(customer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found.");
        return customer;
    }

    @CrossOrigin(origins = "http://localhost:8900")
    @GetMapping("/customer/cards/{customerId}")
    List<StarbucksCard> getCards(@PathVariable String customerId){
        Customer customer = repository.findByCustomerId(customerId);

        if(customer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found.");
        return cardRepository.findByCustomer(customer); 
    }
}