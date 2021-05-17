package com.spring.starbuckscashier.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
//end::baseClass[]
import org.springframework.web.bind.annotation.PostMapping;
//tag::baseClass[]
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import com.spring.starbuckscashier.Order;

@Slf4j
@Controller
@RequestMapping("/office")           
public class BackOfficeController {
    @GetMapping
    public String office(){
      return "backOffice";
    } 


}
