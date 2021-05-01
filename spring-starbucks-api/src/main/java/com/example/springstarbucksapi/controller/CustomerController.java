package com.example.springstarbucksapi;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

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

@RestController
public class CustomerController {
    private final CustomerRepository repository;

    CustomerController(CustomerRepository repository){
        this.repository = repository;
    }

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    Customer newCustomer(@RequestBody Customer customer){
        System.out.println("Creating Customer: #" + customer.getCustomerId());
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