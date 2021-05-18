package com.example.springstarbucksapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
//end::baseClass[]
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//tag::baseClass[]
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.springstarbucksapi.model.Customer;
import com.example.springstarbucksapi.repository.CustomerRepository;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/office")           
public class BackOfficeController {
  @Autowired
  private CustomerRepository repository;
    
  @GetMapping
  public String office(){
    return "backOffice";
  }


    @ModelAttribute
    public void addCustomersToModel(Model model){
      List<Customer> customerList = (List<Customer>)repository.findAll();
      model.addAttribute("customer", customerList);      
      model.addAttribute("updatedCustomer", new Customer());
    } 
    @PostMapping
    public String updateRewards(@ModelAttribute("updatedCustomer") Customer customer){
      Customer updatedCustomer = repository.findByCustomerId(customer.getCustomerId());
      updatedCustomer.setRewardsPoints(customer.getRewardsPoints());
      repository.save(updatedCustomer);
      return "backOffice";
    }


}
