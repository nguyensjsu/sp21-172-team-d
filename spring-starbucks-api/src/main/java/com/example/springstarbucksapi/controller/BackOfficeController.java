package com.example.springstarbucksapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller            // <1>
public class BackOfficeController {

  @GetMapping("/")     // <2>
  public String home() {
    return "home";     // <3>
  }

}
