package com.example.springstarbucksapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
//end::baseClass[]
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
      // for(Customer c : customerList){
      //   System.out.println(c.getCustomerId());
      //   model.addAttribute("customer",c);
      // }

    } 
    @PostMapping
    public String updateRewards(Customer customer){
      System.out.println(customer.getCustomerId());
      System.out.println(customer.getRewardsPoints());
      return "backOffice";
    }


}
