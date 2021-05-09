package com.example.springstarbucksapi.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;  // methodOn, linkTo...

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

import com.example.springstarbucksapi.model.*;
import com.example.springstarbucksapi.repository.*;

@Slf4j
@RestController
public class CardController {

    private final CardRepository repository;
    // private final CardModelAssembler assembler;

    CardController(CardRepository repository) {
        this.repository = repository;
    }

    /*
        POST 	/cards
        Create a new Starbucks Card.

		{
            "CardNumber": "498498082",
            "CardCode": "425",
            "Balance": 20,
            "Activated": false,
            "Status": "New Card."
		}
    */
    @PostMapping("/cards")
    StarbucksCard newCard() {
        StarbucksCard newCard = new StarbucksCard();

        // TODO: Issue #18 Consolidate new card logic
        long num = ThreadLocalRandom.current().nextLong(900000000L) + 10000000000L;
        int code = ThreadLocalRandom.current().nextInt(900) + 100;

        newCard.setCardNumber(String.valueOf(num));
        newCard.setCardCode(String.valueOf(code));
        newCard.setBalance(new BigDecimal("20.00"));
        newCard.setActivated(false);
        newCard.setStatus("New Card");

        log.info("Created new card " + newCard);
        return repository.save(newCard);
    }

    /*
        GET 	/cards/{num}
        Get the details of a specific Starbucks Card.

        {
            "CardNumber": "627131848",
            "CardCode": "547",
            "Balance": 20,
            "Activated": false,
            "Status": ""
        }		
    */
    @GetMapping("/cards/{num}")
    StarbucksCard one(@PathVariable String num) {

        StarbucksCard card = repository.findByCardNumber(num)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Card not found!"));

        return card;
    }

    /*
     * GET /cards Get a list of Starbucks Cards (along with balances).
     * 
     * [ { "CardNumber": "498498082", "CardCode": "425", "Balance": 20, "Activated":
     * false, "Status": "" }, { "CardNumber": "627131848", "CardCode": "547",
     * "Balance": 20, "Activated": false, "Status": "" } ]
     */
    @GetMapping("/cards")
    List<StarbucksCard> all() {
        return repository.findAll();
    }

    /*    
        POST 	/card/activate/{num}/{code}
        Activate Card 
    
        {
            "CardNumber": "627131848",
            "CardCode": "547",
            "Balance": 20,
            "Activated": true,
            "Status": ""
        }
    */
    @PostMapping("/card/activate/{num}/{code}")
    StarbucksCard activate(@PathVariable String num, @PathVariable String code) {

        StarbucksCard card = repository.findByCardNumber(num)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Card not found!"));

        if (card.getCardCode().equals(code)) {
            if (! card.isActivated()) {
                card.setActivated(true);
                repository.save(card);
            }
            else {
                throw new ResponseStatusException(HttpStatus.ACCEPTED, "Card already activated!");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Card not found!");
        }

        log.info("Activated card " + card);
        return card;
    }

    // Not required for assignment
    @DeleteMapping("/cards/{id}")
    ResponseEntity<?> deleteCard(@PathVariable Long id) {
        repository.deleteById(id);
        log.info("Deleted card " + id);
        return ResponseEntity.noContent().build();
    }

    /*
        DELETE 	/cards
        Delete all Cards (Use for Unit Testing Teardown)

        {
        "Status": "All Cards Cleared!"
        }

    */
    @DeleteMapping("/cards")
    ResponseEntity<?> deleteAllCards() {
        repository.deleteAll();
        log.info("Deleted all cards");
        return ResponseEntity.noContent().build();
    }
}
