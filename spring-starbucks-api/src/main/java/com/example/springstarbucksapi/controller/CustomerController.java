package com.example.springstarbucksapi;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import com.example.springstarbucksapi.model.Customer;

// import com.example.springstarbucksapi.model.Customer;
// import com.example.springstarbucksapi.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RestController
public class CustomerController {
    private final CustomerRepository repository;
    
    @Autowired
    private CardRepository cardRepository; 
    
    CustomerController(CustomerRepository repository){
        this.repository = repository;
    }

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    Customer newCustomer(@RequestBody Customer customer){ //Needs to contain the customerId in the body of the request
        System.out.println("Creating Customer: #" + customer.getCustomerId());
        StarbucksCard newCard = new StarbucksCard();

        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 100;
        newCard.setCardNumber(String.valueOf(num));
        newCard.setCardCode(String.valueOf(code));
        newCard.setBalance(20.00);
        newCard.setActive(true);
        newCard.setStatus("New Card");
        cardRepository.save(newCard);
        customer.addCard(newCard);
        Customer new_customer = repository.save(customer);
        return new_customer;
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
}