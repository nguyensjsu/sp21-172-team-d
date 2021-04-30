package com.example.springstarbucksapi;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
	GET 	/ping
		Ping Health Check.

		{
		  "Test": "Starbucks API version 1.0 alive!"
		}		
*/
@RestController
class PingController {

	@Data
	@AllArgsConstructor
	class Ping {
		@NotNull
		private String message;
	}


    @GetMapping("/ping")
    public Ping ping() {
		return new Ping("Starbucks API version 1.0 alive!");
    }
}
