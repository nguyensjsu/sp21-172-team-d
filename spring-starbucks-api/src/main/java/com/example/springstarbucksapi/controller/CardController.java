package com.example.springstarbucksapi.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.springstarbucksapi.model.*;
import com.example.springstarbucksapi.repository.*;
@RestController
public class CardController {
    private final CardRepository repository;

    class Message {
        private String status;
        
        public String getStatus() {
            return status;
        }
        public void setStatus(String msg) {
            status = msg;
        }
    }

    CardController(CardRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/cards")
    StarbucksCard newCard() {
        StarbucksCard newCard = new StarbucksCard();

        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 100;

        newCard.setCardNumber(String.valueOf(num));
        newCard.setCardCode(String.valueOf(code));
        newCard.setBalance(20.00);
        newCard.setActive(false);
        newCard.setStatus("New Card");
        return repository.save(newCard);
    }

    @GetMapping("/cards") 
    List<StarbucksCard> allCards() {
        return (List<StarbucksCard>) repository.findAll();
    }

    @DeleteMapping("/cards")
    Message deleteAll() {
        repository.deleteAll();
        Message msg = new Message();
        msg.setStatus("All Cards Cleared!");
        return msg;
    }

    @CrossOrigin(origins = "http://localhost:8900")
    @GetMapping("/cards/{num}")
    StarbucksCard getOne(@PathVariable String num, HttpServletResponse res) {
        StarbucksCard card = repository.findByCardNumber(num);
        if(card == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card Not Found.");
        return card;
    }

    @PostMapping("/card/activate/{num}/{code}")
    StarbucksCard activate(@PathVariable String num, @PathVariable String code, HttpServletResponse res) {
        StarbucksCard card = repository.findByCardNumber(num);

        if(card == null) 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card Not Found.");
        if(card.getCardCode().equals(code)) {
            card.setActive(true);
            repository.save(card);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card Not Valid.");
        }
        return card;
    }

    @CrossOrigin(origins = "http://localhost:8900")
    @PostMapping("/card/load/{num}/{amount}")
    StarbucksCard loadCard(@PathVariable String num, @PathVariable String amount, HttpServletResponse res) {
        StarbucksCard card = repository.findByCardNumber(num);

        if(card == null) 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card Not Found.");

        double currentBalance = card.getBalance();
        double newBalance = currentBalance + Double.parseDouble(amount);
        card.setBalance(newBalance);

        repository.save(card);
        return card;
    }
}
