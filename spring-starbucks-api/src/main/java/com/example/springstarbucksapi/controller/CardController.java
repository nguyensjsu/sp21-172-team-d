package com.example.springstarbucksapi.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.springstarbucksapi.model.*;
import com.example.springstarbucksapi.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventData;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;


@RestController
public class CardController {
    private final CardRepository repository;
    String endpointSecret = "whsec_UL8UQGRyWWABISRZEwyTkIgs8zdXB7do";

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

    @PostMapping("/api/cards")
    StarbucksCard newCard() {
        StarbucksCard newCard = new StarbucksCard();

        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 100;

        newCard.setCardNumber(String.valueOf(num));
        newCard.setCardCode(String.valueOf(code));
        newCard.setBalance(new BigDecimal(20.00));
        newCard.setActive(false);
        newCard.setStatus("New Card");
        return repository.save(newCard);
    }

    @GetMapping("/api/cards") 
    List<StarbucksCard> allCards() {
        return (List<StarbucksCard>) repository.findAll();
    }

    @DeleteMapping("/api/cards")
    Message deleteAll() {
        repository.deleteAll();
        Message msg = new Message();
        msg.setStatus("All Cards Cleared!");
        return msg;
    }

    @CrossOrigin(origins = "http://localhost:8900")
    @GetMapping("/api/cards/{num}")
    StarbucksCard getOne(@PathVariable String num, HttpServletResponse res) {
        StarbucksCard card = repository.findByCardNumber(num)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Card Not Found"));
        return card;
    }

    @PostMapping("/api/card/activate/{num}/{code}")
    StarbucksCard activate(@PathVariable String num, @PathVariable String code, HttpServletResponse res) {
        StarbucksCard card = repository.findByCardNumber(num)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Card Not Found"));

        if(card.getCardCode().equals(code)) {
            card.setActive(true);
            repository.save(card);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card Not Valid.");
        }
        return card;
    }

    @PostMapping("/webhook")
    String loadCard(@RequestBody String stripeJsonEvent, @RequestHeader("Stripe-Signature") String sigHeader, HttpServletResponse response) {
        Event event = Event.GSON.fromJson(stripeJsonEvent, Event.class);

        //check for valid Stripe request
        try {
            event = Webhook.constructEvent(stripeJsonEvent, sigHeader, endpointSecret);
          } catch (JsonSyntaxException e) {
            // Invalid payload
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "";
          } catch (SignatureVerificationException e) {
            // Invalid signature
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "";
          }

        if ("checkout.session.completed".equals(event.getType())) {
            ObjectMapper m = new ObjectMapper();

            //map JSON object to a Map object
            Map<String, Object> props = m.convertValue(event.getData(), Map.class);
            Object dataMap = props.get("object");
            Map<String, Object> objectMapper = m.convertValue(dataMap, Map.class);

            String cardNum = (String) objectMapper.get("clientReferenceId");
            BigDecimal amount = new BigDecimal((long) objectMapper.get("amountTotal")).divide(new BigDecimal(100));

            StarbucksCard card = repository.findByCardNumber(cardNum)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Card Not Found"));
           
            BigDecimal currentBalance = card.getBalance();
            BigDecimal newBalance = currentBalance.add(amount);
            card.setBalance(newBalance);
            repository.save(card);
            System.out.println("loading money into card");
        }

        return "";
    }
}
