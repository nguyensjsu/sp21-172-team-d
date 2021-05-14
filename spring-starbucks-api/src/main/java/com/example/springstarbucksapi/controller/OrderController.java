package com.example.springstarbucksapi.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;  // methodOn, linkTo...

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

import com.example.springstarbucksapi.model.*;
import com.example.springstarbucksapi.repository.*;

@Slf4j
@RestController
class OrderController {

    private final OrderRepository orderRepository;
    @Autowired private CardRepository cardRepository;
    @Autowired private PriceRepository priceRepository;
    @Autowired private RegisterRepository registerRepository;
    //class Message{}

    private HashMap<String, Order> orders = new HashMap<>();
    
    OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /*		
    POST    /order/register/{regid}
        Create a new order. Set order as "active" for register.

        Request:

	    {
            "Drink": "Latte",
            "Milk":  "Whole",
            "Size":  "Grande"
	    }         

	    Response:

		{
            "Drink": "Latte",
            "Milk": "Whole",
            "Size": "Grande",
            "Total": 2.413125,
            "Status": "Ready for Payment."
        }
    */
    @PostMapping("/order/register/{regid}")
    @ResponseStatus(HttpStatus.CREATED)
    Order newOrder(@PathVariable Long regid, @RequestBody Order requestedOrder) {
        log.info("Received an order POST request for " + requestedOrder + " at register " + regid);
        
        Register currentRegister = registerRepository.findById(regid)
        .orElse(new Register(regid));
        
        // If there is an active order in status "Ready for payment" then abort
        Order existingOrder = currentRegister.getOrder();
        if (existingOrder == null) {
            log.info("Register " + regid + " is available!");
        } else {
            log.info("Register " + regid + " has an existing order.  Checking status...");
            orderRepository.findById(currentRegister.getOrder().getId())
            .filter(order -> order.getStatus().equals("Ready for payment"))
                    .ifPresent(status -> {
                        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error: This register is currently processing another order.");
                    });
            log.info("Register " + regid + " is available!");
        }

        log.info("Looking up drink price...");
        Price orderPrice = priceRepository.findByDrinkIgnoreCaseAndSizeIgnoreCase(requestedOrder.getDrink(), requestedOrder.getSize())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Invalid order selection!"));
        
        log.info("Calculating total...");
        // Let's do all the calculations that would be shown on the receipt
        BigDecimal taxRate = new BigDecimal("0.0775");
        BigDecimal subtotal = orderPrice.getPrice();
        BigDecimal tax = subtotal.multiply(taxRate);
        BigDecimal total = subtotal.add(tax);
        
        requestedOrder.setTotal(total);
        requestedOrder.setStatus("Ready for payment");
        
        log.info("Saving order and register " + currentRegister.getId());
        Order savedOrder = orderRepository.save(requestedOrder);
        currentRegister.setOrder(savedOrder);
        registerRepository.save(currentRegister);

        return savedOrder;
        
    }
    
    /*
    GET     /order/register/{regid}
        Request the current state of the "active" Order.

		{
		  "Drink": "Latte",
		  "Milk": "Whole",
		  "Size": "Grande",
		  "Total": 2.413125,
		  "Status": "Ready for Payment."
		}
    */
    @GetMapping("/order/register/{regid}")
    @ResponseStatus(HttpStatus.OK)
    Order getStatus(@PathVariable Long regid) {
        log.info("Received an order GET request for register " + regid);
        
        Register currentRegister = registerRepository.findById(regid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Invalid register / no order yet!"));
        
        return currentRegister.getOrder();
    }
    
    /*
    DELETE  /order/register/{regid}
        Clear the "active" Order.

		{
		  "Status": "Active Order Cleared!"
		}
    */
    @DeleteMapping("/order/register/{regid}")
    ResponseEntity<?> cancelActiveOrder(@PathVariable Long regid) {
        log.info("Received an order DELETE request for register " + regid);
        
        // TODO: Consider throw vs return.. sounds like throw should be for logic/server errors, return for user errors
        Register currentRegister = registerRepository.findById(regid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Invalid register / no order yet!"));
                
        Order existingOrder = currentRegister.getOrder();
        if (existingOrder == null) {
            log.info("No order to delete!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No order to delete!");
        }
        
        // TODO: Business logic: What statuses can/can't be cancelled?
        // For now, let's only allow to cancel if not already paid

        // If order is NOT paid, cancel it.
        if (! existingOrder.getStatus().equals("Paid")) {
            log.info("Cancelling incomplete order before clearing register.");
            existingOrder.setStatus("Cancelled");
            Order cancelledOrder = orderRepository.save(existingOrder);
        } else {
            log.info("Not modifying order - already completed.  Register will be cleared.");
        }

        currentRegister.setOrder(null);
        registerRepository.save(currentRegister);
        log.info("Register " + regid + " is now available!");
        return ResponseEntity.ok().body("Active Order Cleared!");
        
        // return ResponseEntity //
        //         .status(HttpStatus.METHOD_NOT_ALLOWED) //
        //         .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
        //         .body(Problem.create() //
        //                 .withTitle("Method not allowed") //
        //                 .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
    }

    /*
    POST    /order/register/{regid}/pay/{cardnum}
        Process payment for the "active" Order. 

        Response: (with updated card balance)

		{
		  "CardNumber": "627131848",
		  "CardCode": "547",
		  "Balance": 15.17375,
		  "Activated": true,
		  "Status": ""
		}
    */
    @PostMapping("/order/register/{regid}/pay/{cardNumber}")
    @ResponseStatus(HttpStatus.OK)
    StarbucksCard processOrder(@PathVariable Long regid, @PathVariable String cardNumber) {
        log.info("Received an order payment POST request for register " + regid);

        Register currentRegister = registerRepository.findById(regid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Invalid register / no order yet!"));

        Order currentOrder = currentRegister.getOrder();
        if (currentOrder == null) {
            log.info("No order to pay!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This register is not processing an order.");
        }

        log.info("Register " + regid + " handling payment for order " + currentOrder.getId());

        // Find card
        StarbucksCard paymentCard = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Invalid card!"));

        //Check card code?
        // if (!paymentCard.getCardCode().equals(code)) {
        //     throw new ResponseStatusException(HttpStatus.ACCEPTED, "Error: Invalid card!");
        // }

        // Check if card is active
        if (! paymentCard.isActivated()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Invalid card!");
        }

        // Check card balance -- for now, require full payment from card (no partial payment support)
        // 409 = conflict, 422 = unprocessable (some say due to "semantic errors" so I think 409 is better)
        // if card balance is < order total
        if (paymentCard.getBalance().compareTo(currentOrder.getTotal()) < 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: Insufficient funds");
        }

        // TODO: Move this to a method in cardRepository
        paymentCard.setBalance(paymentCard.getBalance().subtract(currentOrder.getTotal()));
        StarbucksCard usedPaymentCard = cardRepository.save(paymentCard);

        currentOrder.setStatus("Paid");
        currentRegister.setOrder(orderRepository.save(currentOrder));
        registerRepository.save(currentRegister);

        // I've chosen not to delete the order from the register yet, so for now it leave the previous transaction up like a gas pump
        // log.info("Register " + regid + " is now available!");

        return usedPaymentCard;
    }


    /*
    GET     /orders
        Get a list of all active orders (for all registers)

		{
		  "5012349": {
		    "Drink": "Latte",
		    "Milk": "Whole",
		    "Size": "Grande",
		    "Total": 4.82625,
		    "Status": "Paid with Card: 627131848 Balance: $15.17."
		  }
		}
    */
    @GetMapping("/orders")
<<<<<<< HEAD
    List<Order> allOrders() {
        return (List<Order>) repository.findAll();
=======
    // CollectionModel<EntityModel<Order>> all() {
    List<Order> all() {
        log.info("Received an order listing GET request for ALL orders/registers");
        return orderRepository.findAll();
>>>>>>> main
    }

    /*
    DELETE 	/orders
		Delete all Orders (Use for Unit Testing Teardown)

		{
		  "Status": "All Orders Cleared!"
		}

    */
    @DeleteMapping("/orders")
    ResponseEntity<?> deleteAllOrders() {
        log.info("Received an order DELETE request for ALL orders/registers");

        // TODO: use jpa/spring features to do this automatically
        // @Transactional required for register update query
        log.info("Clearing orders from all registers before deleting all orders");
        registerRepository.disassociateFromAllOrders();
        
        log.info("Deleting all orders");
        orderRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
